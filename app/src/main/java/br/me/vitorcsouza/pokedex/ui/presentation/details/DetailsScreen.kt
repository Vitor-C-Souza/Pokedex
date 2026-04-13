package br.me.vitorcsouza.pokedex.ui.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.me.vitorcsouza.pokedex.domain.model.EvolutionInfo
import br.me.vitorcsouza.pokedex.domain.model.MoveInfo
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.AboutPokemonView
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.BaseStatesView
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.DetailsTopBar
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.EvolutionView
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.HeightOrWeightCard
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.MovesView
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onSeeAllMovesClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.error != null) {
            Text(
                text = state.error ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            state.pokemon?.let {
                DetailsScreenContent(
                    pokemon = it,
                    modifier = modifier,
                    onBackClick = onBackClick,
                    onFavoriteClick = { viewModel.toggleFavorite() },
                    onSeeAllMovesClick = { onSeeAllMovesClick(it.name.orEmpty()) }
                )
            }
        }
    }
}

@Composable
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onSeeAllMovesClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val cardColor = pokemon.pokemonTypes.firstOrNull()?.color ?: Color.LightGray

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(300.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                BaseStatesView(pokemon = pokemon)
                AboutPokemonView(pokemon = pokemon)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    pokemon.height?.let {
                        HeightOrWeightCard(
                            modifier = Modifier.weight(1f),
                            title = "Height",
                            heightOrWeight = it
                        )
                    }
                    pokemon.weight?.let {
                        HeightOrWeightCard(
                            modifier = Modifier.weight(1f),
                            title = "Weight",
                            heightOrWeight = it
                        )
                    }
                }
                MovesView(
                    moves = pokemon.moves.orEmpty().take(10),
                    onSeeAllClick = onSeeAllMovesClick
                )
                Spacer(modifier = Modifier.height(16.dp))
                EvolutionView(
                    evolutions = pokemon.evolutions.orEmpty(),
                    cardColor = cardColor
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        DetailsTopBar(
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick,
            pokemon = pokemon,
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(10f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenPreview() {
    DetailsScreenContent(
        pokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            types = listOf("grass", "poison"),
            weight = 69.0,
            height = 7.0,
            hp = 45,
            attack = 49,
            defense = 49,
            specialAttack = 65,
            specialDefense = 65,
            speed = 45,
            description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger.",
            isFavorite = true,
            moves = listOf(
                MoveInfo(
                    name = "Tackle",
                    type = "normal",
                    description = "Does a quick, low-power punch",
                    power = 40,
                    accuracy = 100,
                    pp = 35
                ),
                MoveInfo(
                    name = "Vine Whip",
                    type = "grass",
                    description = "Strikes the target with slender, whiplike vines",
                    power = 45,
                    accuracy = 100,
                    pp = 25
                )
            ),
            evolutions = listOf(
                EvolutionInfo(
                    name = "Ivysaur",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png"
                ),
                EvolutionInfo(
                    name = "Venusaur",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png"
                )
            )
        ),
        onBackClick = {},
        onFavoriteClick = {},
        onSeeAllMovesClick = {}
    )
}
