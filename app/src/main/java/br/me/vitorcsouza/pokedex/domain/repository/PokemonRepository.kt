package br.me.vitorcsouza.pokedex.domain.repository

import br.me.vitorcsouza.pokedex.domain.model.Pokemon

interface PokemonRepository {
    suspend fun listPokemon(limit: Int, offset: Int): Result<List<Pokemon>>
}