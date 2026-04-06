package br.me.vitorcsouza.pokedex.data.repository

import br.me.vitorcsouza.pokedex.data.local.PokemonDao
import br.me.vitorcsouza.pokedex.data.mapper.toDomain
import br.me.vitorcsouza.pokedex.data.mapper.toEntity
import br.me.vitorcsouza.pokedex.data.remote.PokeApi
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.first

class PokemonRepositoryImpl(
    private val api: PokeApi,
    private val dao: PokemonDao
) : PokemonRepository {

    override suspend fun listPokemon(
        limit: Int,
        offset: Int
    ): Result<List<Pokemon>> {
        return try {
            // 1. Tenta buscar no banco apenas o intervalo solicitado
            val localPokemon = dao.getAllPokemon().first()

            // 2. Se o que temos no banco é menor que o offset + limit, buscamos na API
            if (localPokemon.size < offset + limit) {
                val response = api.getPokemonList(limit, offset)
                
                val entities = response.results.map { basicPokemon ->
                    val details = api.getPokemonDetail(basicPokemon.name)
                    details.toEntity()
                }

                dao.insertPokemonList(entities)
            }
            
            // 3. Busca APENAS os itens da página atual para evitar renderizar tudo de novo na UI
            val allUpdated = dao.getAllPokemon().first().sortedBy { it.id }
            
            // Retornamos apenas os novos pokemons (a fatia da página)
            val fromIndex = offset.coerceAtMost(allUpdated.size)
            val toIndex = (offset + limit).coerceAtMost(allUpdated.size)
            
            val pageItems = allUpdated.subList(fromIndex, toIndex).map { it.toDomain() }
            
            Result.success(pageItems)
            
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
