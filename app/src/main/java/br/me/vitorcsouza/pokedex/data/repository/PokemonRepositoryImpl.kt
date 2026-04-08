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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

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

    override suspend fun catchPokemonByNameOrId(nameOrId: String): Result<Pokemon> {
        return try {
            val localPokemon = dao.getAllPokemon().first()
            
            // Busca na API (sempre em minúsculo e sem espaços)
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
                    evolutions.add(EvolutionInfo(chain.species.name, imageUrl))
                    chain.evolvesTo.forEach { addEvolution(it) }
                }
                addEvolution(ecoChain.chain)
            }

            val existing = localPokemon.find { it.id == details.id }

            val pokemon = details.toDomain().copy(
                description = description,
                isFavorite = existing?.isFavorite ?: false,
                moves = details.moves.map {
                    MoveInfo(
                        name = it.move.name,
                        type = "",
                        description = "",
                        power = null,
                        accuracy = null,
                        pp = null
                    )
                },
                evolutions = evolutions
            )

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
