package br.me.vitorcsouza.pokedex.ui.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.pokedex.domain.usecase.GetAllPokemon
import br.me.vitorcsouza.pokedex.domain.usecase.GetFavoritePokemon
import br.me.vitorcsouza.pokedex.domain.usecase.SearchPokemon
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllPokemon: GetAllPokemon,
    private val searchPokemon: SearchPokemon,
    private val getFavoritePokemon: GetFavoritePokemon
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    var state: HomeState by mutableStateOf(_state.value)

//    private val _items = MutableStateFlow(emptyList())
//    val items: StateFlow = _items

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
            delay(500)

            val trimmedQuery = newQuery.trim().lowercase()

            if (trimmedQuery.isBlank()) {
                state = state.copy(page = 0, pokemonList = emptyList(), endReached = false)
                loadPokemon()
            } else if (trimmedQuery == "favoritos" || trimmedQuery == "favorites") {
                state = state.copy(isLoading = true)
                getFavoritePokemon().collectLatest { results ->
                    state = state.copy(
                        pokemonList = results,
                        isLoading = false,
                        endReached = true
                    )
                }
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
