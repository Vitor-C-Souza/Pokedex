package br.me.vitorcsouza.pokedex.ui.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.AboutPokemonView
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.BaseStatesView
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.DetailsTopBar
import br.me.vitorcsouza.pokedex.ui.presentation.details.components.HeightOrWeightCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val state = viewModel.state
    state.pokemon?.let {
        DetailsScreenContent(
            state = state,
            pokemon = it,
            modifier = modifier,
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )
    }
}

@Composable
fun DetailsScreenContent(
    modifier: Modifier = Modifier,
    state: DetailsState,
    pokemon: Pokemon,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            DetailsTopBar(
                onBackClick = onBackClick,
                onFavoriteClick = onFavoriteClick,
                pokemon = pokemon
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BaseStatesView(pokemon = pokemon)
            AboutPokemonView(pokemon = pokemon)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HeightOrWeightCard(
                    modifier = Modifier.weight(1f),
                    title = "Height",
                    heightOrWeight = pokemon.height
                )
                HeightOrWeightCard(
                    modifier = Modifier.weight(1f),
                    title = "Weight",
                    heightOrWeight = pokemon.weight
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    DetailsScreenContent(
        state = DetailsState(),
        pokemon = Pokemon(
            id = 1,
            name = "bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            types = listOf("grass", "poison"),
            weight = 69,
            height = 7,
            hp = 45,
            attack = 49,
            defense = 49,
            specialAttack = 65,
            specialDefense = 65,
            speed = 45,
            description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger."
        ),
        onBackClick = {},
        onFavoriteClick = {}
    )
}