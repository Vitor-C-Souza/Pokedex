package br.me.vitorcsouza.pokedex.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.pokedex.domain.usecase.GetAllPokemon
import br.me.vitorcsouza.pokedex.domain.usecase.GetFavoritePokemon
import br.me.vitorcsouza.pokedex.domain.usecase.SearchPokemon
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllPokemon: GetAllPokemon,
    private val searchPokemon: SearchPokemon,
    private val getFavoritePokemon: GetFavoritePokemon
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val pageSize = 20
    private var searchJob: Job? = null

    init {
        loadPokemon()
    }

    fun loadPokemon() {
        val currentState = _state.value
        if (currentState.isPaginateLoading || currentState.endReached || currentState.searchQuery.isNotBlank()) return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isPaginateLoading = true,
                    isLoading = it.pokemonList.isEmpty()
                )
            }

            val offset = _state.value.page * pageSize
            
            getAllPokemon(pageSize, offset)
                .onSuccess { newList ->
                    _state.update {
                        it.copy(
                            pokemonList = it.pokemonList + newList,
                            isLoading = false,
                            isPaginateLoading = false,
                            endReached = newList.isEmpty(),
                            page = it.page + 1,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            error = error.message,
                            isLoading = false,
                            isPaginateLoading = false
                        )
                    }
                }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _state.update { it.copy(searchQuery = newQuery) }
        
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)

            val trimmedQuery = newQuery.trim().lowercase()

            if (trimmedQuery.isBlank()) {
                _state.update { it.copy(page = 0, pokemonList = emptyList(), endReached = false) }
                loadPokemon()
            } else if (trimmedQuery == "favoritos" || trimmedQuery == "favorites") {
                _state.update { it.copy(isLoading = true) }
                getFavoritePokemon().collectLatest { results ->
                    _state.update {
                        it.copy(
                            pokemonList = results,
                            isLoading = false,
                            endReached = true
                        )
                    }
                }
            } else {
                _state.update { it.copy(isLoading = true) }
                searchPokemon(newQuery).collectLatest { results ->
                    _state.update {
                        it.copy(
                            pokemonList = results,
                            isLoading = false,
                            endReached = true
                        )
                    }
                }
            }
        }
    }
}
