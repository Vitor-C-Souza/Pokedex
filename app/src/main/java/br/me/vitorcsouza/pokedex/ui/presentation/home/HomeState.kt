package br.me.vitorcsouza.pokedex.ui.presentation.home

import br.me.vitorcsouza.pokedex.domain.model.Pokemon

data class HomeState(
    val isLoading: Boolean = false,
    val pokemonList: List<Pokemon> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)
