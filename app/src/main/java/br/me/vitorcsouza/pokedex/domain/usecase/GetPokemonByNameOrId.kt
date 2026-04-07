package br.me.vitorcsouza.pokedex.domain.usecase

import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.domain.repository.PokemonRepository

class GetPokemonByNameOrId(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(nameOrId: String): Result<Pokemon> {
        return repository.catchPokemonByNameOrId(nameOrId)
    }
}
