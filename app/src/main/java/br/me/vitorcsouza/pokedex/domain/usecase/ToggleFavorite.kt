package br.me.vitorcsouza.pokedex.domain.usecase

import br.me.vitorcsouza.pokedex.domain.repository.PokemonRepository

class ToggleFavorite(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId: Int, isFavorite: Boolean) {
        repository.toggleFavorite(pokemonId, isFavorite)
    }
}
