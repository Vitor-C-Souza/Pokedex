package br.me.vitorcsouza.pokedex.ui.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.domain.model.PokemonType
import br.me.vitorcsouza.pokedex.ui.components.TypeBadge
import coil.compose.AsyncImage

@Composable
fun DetailsTopBar(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val primaryType = pokemon.pokemonTypes.firstOrNull() ?: PokemonType.UNKNOWN
    val cardColor = primaryType.color

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .matchParentSize()
                .clip(RoundedCornerShape(0.dp, 0.dp, 40.dp, 40.dp))
                .background(cardColor)
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .offset(x = 120.dp, y = (-50).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f))
                    .align(Alignment.TopEnd),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.15f))
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = pokemon.formattedId,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    pokemon.pokemonTypes.forEach { type ->
                        TypeBadge(type = type)
                    }
                }
            }
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
                    .offset(y = 60.dp)
                    .zIndex(10f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailsTopBarPreview() {
    DetailsTopBar(
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
            description = "Bulbasaur can be seen napping in bright sunlight. There is a seed on its back."
        ),
        onBackClick = {},
        onFavoriteClick = {}
    )
}
