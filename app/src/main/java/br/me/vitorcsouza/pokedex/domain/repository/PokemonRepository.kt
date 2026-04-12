package br.me.vitorcsouza.pokedex.domain.repository

import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun listPokemon(limit: Int, offset: Int): Result<List<Pokemon>>
    fun searchPokemon(query: String): Flow<List<Pokemon>>
    suspend fun catchPokemonByNameOrId(nameOrId: String): Result<Pokemon>
    suspend fun toggleFavorite(pokemonId: Int, isFavorite: Boolean)
    fun getFavoritePokemon(): Flow<List<Pokemon>>

    suspend fun syncAllPokemons(limit: Int)
}
