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
        StatsUnique(title = "HP", value = pokemon.hp, barColor = cardColor)
        StatsUnique(title = "Attack", value = pokemon.attack, barColor = cardColor)
        StatsUnique(title = "Defense", value = pokemon.defense, barColor = cardColor)
        StatsUnique(title = "Special Attack", value = pokemon.specialAttack, barColor = cardColor)
        StatsUnique(title = "Special Defense", value = pokemon.specialDefense, barColor = cardColor)
        StatsUnique(title = "Speed", value = pokemon.speed, barColor = cardColor)
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
            weight = 69,
            height = 7,
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