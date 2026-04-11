package br.me.vitorcsouza.pokedex.data.repository

import br.me.vitorcsouza.pokedex.data.local.PokemonDao
import br.me.vitorcsouza.pokedex.data.mapper.toDomain
import br.me.vitorcsouza.pokedex.data.mapper.toEntity
import br.me.vitorcsouza.pokedex.data.remote.PokeApi
import br.me.vitorcsouza.pokedex.data.remote.dto.ChainLinkDto
import br.me.vitorcsouza.pokedex.domain.model.EvolutionInfo
import br.me.vitorcsouza.pokedex.domain.model.MoveInfo
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale

class PokemonRepositoryImpl(
    private val api: PokeApi,
    private val dao: PokemonDao
) : PokemonRepository {

    override suspend fun listPokemon(
        limit: Int,
        offset: Int
    ): Result<List<Pokemon>> {
        return try {
            val localPokemon = dao.getAllPokemon().first()

            if (localPokemon.size < offset + limit) {
                val response = api.getPokemonList(limit, offset)
                
                val entities = response.results.map { basicPokemon ->
                    val details = api.getPokemonDetail(basicPokemon.name)
                    val species = api.getPokemonSpecies(details.id)
                    val description = species.flavorTextEntries
                        .find { it.language.name == "en" }
                        ?.flavorText
                        ?.replace("\n", " ")
                        ?.replace("\u000c", " ") ?: ""

                    val existing = localPokemon.find { it.id == details.id }
                    details.toEntity(description, isFavorite = existing?.isFavorite ?: false)
                }

                dao.insertPokemonList(entities)
            }
            
            val allUpdated = dao.getAllPokemon().first().sortedBy { it.id }
            val fromIndex = offset.coerceAtMost(allUpdated.size)
            val toIndex = (offset + limit).coerceAtMost(allUpdated.size)
            
            val pageItems = allUpdated.subList(fromIndex, toIndex).map { it.toDomain() }
            
            Result.success(pageItems)
            
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun searchPokemon(query: String): Flow<List<Pokemon>> {
        return dao.searchPokemon(query.trim()).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun catchPokemonByNameOrId(nameOrId: String): Result<Pokemon> =
        coroutineScope {
            try {
                val localPokemon = dao.getAllPokemon().first()

                val details = api.getPokemonDetail(nameOrId.lowercase().trim())
                val species = api.getPokemonSpecies(details.id)
                val description = species.flavorTextEntries
                    .find { it.language.name == "en" }
                    ?.flavorText
                    ?.replace("\n", " ")
                    ?.replace("\u000c", " ") ?: ""

                // Buscar Evoluções
                val evolutions = mutableListOf<EvolutionInfo>()
                species.evolutionChain?.url?.let { url ->
                    val ecoChain = api.getEvolutionChain(url)

                    fun addEvolution(chain: ChainLinkDto) {
                        val id = chain.species.url.split("/").dropLast(1).last()
                        val imageUrl =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

                        val detail = chain.evolutionDetails.firstOrNull()
                        val condition = when {
                            detail?.minLevel != null -> "Level ${detail.minLevel}"
                            detail?.item != null -> detail.item.name.replace("-", " ")
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

                            detail?.minHappiness != null -> "Happiness"
                            detail?.trigger?.name == "trade" -> "Trade"
                            detail?.knownMove != null -> "Knows ${
                                detail.knownMove.name.replace(
                                    "-",
                                    " "
                                )
                            }"

                            detail?.location != null -> "Location: ${
                                detail.location.name.replace(
                                    "-",
                                    " "
                                )
                            }"

                            else -> null
                        }

                        evolutions.add(EvolutionInfo(chain.species.name, imageUrl, condition))
                        chain.evolvesTo.forEach { addEvolution(it) }
                    }
                    addEvolution(ecoChain.chain)
                }

                val moveDetails = details.moves.map { moveEntry ->
                    async {
                        val latestVersionDetail = moveEntry.versionGroupDetails.lastOrNull()
                        val learnMethod = latestVersionDetail?.moveLearnMethod?.name
                        val levelLearnedAt = latestVersionDetail?.levelLearnedAt

                        try {
                            val moveDetail = api.getMoveDetail(moveEntry.move.name)
                            MoveInfo(
                                name = moveDetail.name,
                                type = moveDetail.type.name,
                                description = moveDetail.flavorTextEntries
                                    .find { it.language.name == "en" }
                                    ?.flavorText
                                    ?.replace("\n", " ")
                                    ?.replace("\u000c", " ") ?: "",
                                power = moveDetail.power,
                                accuracy = moveDetail.accuracy,
                                pp = moveDetail.pp,
                                learnMethod = learnMethod,
                                levelLearnedAt = levelLearnedAt
                            )
                        } catch (e: Exception) {
                            MoveInfo(
                                name = moveEntry.move.name,
                                learnMethod = learnMethod,
                                levelLearnedAt = levelLearnedAt
                            )
                        }
                    }
                }.awaitAll()

                val existing = localPokemon.find { it.id == details.id }

                val pokemon = details.toDomain().copy(
                    description = description,
                    isFavorite = existing?.isFavorite ?: false,
                    moves = moveDetails,
                    evolutions = evolutions
                )

                val entity =
                    details.toEntity(description, isFavorite = existing?.isFavorite ?: false)
                dao.insertPokemonList(listOf(entity))

                Result.success(pokemon)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun toggleFavorite(pokemonId: Int, isFavorite: Boolean) {
        dao.updateFavoriteStatus(pokemonId, isFavorite)
    }

    override fun getFavoritePokemon(): Flow<List<Pokemon>> {
        return dao.getFavoritePokemon().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
