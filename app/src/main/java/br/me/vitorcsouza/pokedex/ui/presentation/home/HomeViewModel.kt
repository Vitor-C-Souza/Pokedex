package br.me.vitorcsouza.pokedex.ui.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.pokedex.domain.usecase.GetAllPokemon
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllPokemon: GetAllPokemon
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        loadPokemon()
    }

    private fun loadPokemon() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            getAllPokemon(20, 0)
                .onSuccess { pokemonList ->
                    state = state.copy(
                        pokemonList = pokemonList,
                        isLoading = false,
                        error = null
                    )
                }
                .onFailure {
                    state = state.copy(
                        error = it.message,
                        isLoading = false
                    )
                }
        }
    }

    private fun searchPokemon() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

        }
    }


}