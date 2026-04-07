package br.me.vitorcsouza.pokedex.ui.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.pokedex.domain.usecase.GetAllPokemon
import br.me.vitorcsouza.pokedex.domain.usecase.SearchPokemon
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllPokemon: GetAllPokemon,
    private val searchPokemon: SearchPokemon
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    private val pageSize = 20
    private var searchJob: Job? = null

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        if (state.isPaginateLoading || state.endReached || state.searchQuery.isNotBlank()) return

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
        
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(800)
            
            if (newQuery.isBlank()) {
                state = state.copy(page = 0, pokemonList = emptyList(), endReached = false)
                loadPokemon()
            } else {
                state = state.copy(isLoading = true)
                searchPokemon(newQuery).collectLatest { results ->
                    state = state.copy(
                        pokemonList = results,
                        isLoading = false,
                        endReached = true
                    )
                }
            }
        }
    }
}
