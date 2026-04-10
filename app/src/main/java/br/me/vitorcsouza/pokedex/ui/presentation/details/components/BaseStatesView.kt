package br.me.vitorcsouza.pokedex.ui.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.domain.model.PokemonType

@Composable
fun BaseStatesView(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    val primaryType = pokemon.pokemonTypes.firstOrNull() ?: PokemonType.UNKNOWN
    val cardColor = primaryType.color

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Base Stats",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        pokemon.hp?.let { StatsUnique(title = "HP", value = it, barColor = cardColor) }
        pokemon.attack?.let { StatsUnique(title = "Attack", value = it, barColor = cardColor) }
        pokemon.defense?.let { StatsUnique(title = "Defense", value = it, barColor = cardColor) }
        pokemon.specialAttack?.let {
            StatsUnique(
                title = "Special Attack",
                value = it,
                barColor = cardColor
            )
        }
        pokemon.specialDefense?.let {
            StatsUnique(
                title = "Special Defense",
                value = it,
                barColor = cardColor
            )
        }
        pokemon.speed?.let { StatsUnique(title = "Speed", value = it, barColor = cardColor) }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.labelLarge,
            )

            Text(
                text = ((pokemon.hp ?: 0) + (pokemon.attack ?: 0) + (pokemon.defense
                    ?: 0) + (pokemon.specialAttack ?: 0) + (pokemon.specialDefense
                    ?: 0) + (pokemon.speed ?: 0)).toString(),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
fun StatsUnique(title: String, value: Int, barColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
            )
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.labelSmall,
            )
        }

        LinearProgressIndicator(
            progress = { value / 255f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape),
            color = barColor,
            trackColor = Color.LightGray.copy(alpha = 0.3f),
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BaseStatesViewPreview() {
    BaseStatesView(
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
            speed = 45,
            specialAttack = 65,
            specialDefense = 65,
            description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back. By soaking up the sun's rays, the seed grows progressively larger."
        )
    )
}