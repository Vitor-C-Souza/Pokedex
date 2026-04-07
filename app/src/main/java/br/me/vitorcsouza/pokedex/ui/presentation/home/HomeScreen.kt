package br.me.vitorcsouza.pokedex.ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.ui.presentation.home.components.CardPokemon
import br.me.vitorcsouza.pokedex.ui.presentation.home.components.HomeHeader
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onPokemonClick: (Pokemon) -> Unit = {}
) {
    val state = viewModel.state

    HomeScreenContent(
        state = state,
        modifier = modifier,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
        onLoadNextPage = { viewModel.loadPokemon() },
        onPokemonClick = onPokemonClick
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier,
    state: HomeState,
    onSearchQueryChange: (String) -> Unit = {},
    onLoadNextPage: () -> Unit = {},
    onPokemonClick: (Pokemon) -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            HomeHeader(
                modifier = Modifier.padding(16.dp),
                value = state.searchQuery,
                onValueChange = onSearchQueryChange
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading && state.pokemonList.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(state.pokemonList) { index, pokemon ->
                        if (index >= state.pokemonList.size - 1 && !state.isPaginateLoading && !state.endReached) {
                            onLoadNextPage()
                        }
                        CardPokemon(
                            pokemon = pokemon,
                            onClick = { onPokemonClick(pokemon) }
                        )
                    }


                    if (state.isPaginateLoading) {
                        item(span = { GridItemSpan(2) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        state = HomeState(),
        modifier = Modifier
    )
}
