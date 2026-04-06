package br.me.vitorcsouza.pokedex.domain.usecase

import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.domain.repository.PokemonRepository

class GetAllPokemon(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(limit: Int, offset: Int): Result<List<Pokemon>> {
        return repository.listPokemon(limit, offset)
    }
}
