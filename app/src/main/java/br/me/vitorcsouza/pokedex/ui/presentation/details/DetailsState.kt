package br.me.vitorcsouza.pokedex.ui.presentation.details

import br.me.vitorcsouza.pokedex.domain.model.Pokemon

data class DetailsState(
    val isLoading: Boolean = false,
    val pokemon: Pokemon? = null,
    val error: String? = null
)

