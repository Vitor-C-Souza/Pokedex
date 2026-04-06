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

    private val pageSize = 20

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        if (state.isPaginateLoading || state.endReached) return

        viewModelScope.launch {
            state = state.copy(
                isPaginateLoading = true,
                isLoading = state.pokemonList.isEmpty()
            )
            
            val offset = state.page * pageSize
            
            getAllPokemon(pageSize, offset)
                .onSuccess { newList ->
                    state = state.copy(
                        pokemonList = state.pokemonList + newList,
                        isLoading = false,
                        isPaginateLoading = false,
                        endReached = newList.isEmpty(),
                        page = state.page + 1,
                        error = null
                    )
                }
                .onFailure {
                    state = state.copy(
                        error = it.message,
                        isLoading = false,
                        isPaginateLoading = false
                    )
                }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        state = state.copy(searchQuery = newQuery)
    }
}
