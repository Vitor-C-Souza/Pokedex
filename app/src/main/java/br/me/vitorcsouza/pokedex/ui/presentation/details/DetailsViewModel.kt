package br.me.vitorcsouza.pokedex.ui.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.pokedex.domain.usecase.GetPokemonByNameOrId
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getPokemonByNameOrId: GetPokemonByNameOrId,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(DetailsState())
        private set

    init {
        savedStateHandle.get<String>("pokemonName")?.let { nameOrId ->
            loadPokemon(nameOrId)
        }
    }

    private fun loadPokemon(nameOrId: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            getPokemonByNameOrId(nameOrId).onSuccess {
                state = state.copy(
                    pokemon = it,
                    isLoading = false,
                    error = null
                )
            }.onFailure {
                state = state.copy(
                    error = it.message,
                    isLoading = false
                )
            }
        }
    }
}