package br.me.vitorcsouza.pokedex.ui.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.pokedex.domain.usecase.GetPokemonByNameOrId
import br.me.vitorcsouza.pokedex.domain.usecase.ToggleFavorite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getPokemonByNameOrId: GetPokemonByNameOrId,
    private val toggleFavoriteUseCase: ToggleFavorite,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("pokemonName")?.let { nameOrId ->
            loadPokemon(nameOrId)
        }
    }

    private fun loadPokemon(nameOrId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            getPokemonByNameOrId(nameOrId).onSuccess { pokemon ->
                _state.update {
                    it.copy(
                        pokemon = pokemon,
                        isLoading = false,
                        error = null
                    )
                }

            }.onFailure { exception ->
                _state.update {
                    it.copy(
                        error = exception.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun toggleFavorite() {
        val pokemon = _state.value.pokemon ?: return
        viewModelScope.launch {
            val newFavoriteStatus = !(pokemon.isFavorite ?: false)
            pokemon.id?.let { toggleFavoriteUseCase(it, newFavoriteStatus) }
            _state.update {
                it.copy(
                    pokemon = pokemon.copy(isFavorite = newFavoriteStatus)
                )
            }
        }
    }
}
