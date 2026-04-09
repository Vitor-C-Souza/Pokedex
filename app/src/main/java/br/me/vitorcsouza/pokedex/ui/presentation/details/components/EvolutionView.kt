package br.me.vitorcsouza.pokedex.ui.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.me.vitorcsouza.pokedex.domain.model.EvolutionInfo
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun EvolutionView(
    modifier: Modifier = Modifier,
    evolutions: List<EvolutionInfo>,
    cardColor: Color = Color.LightGray
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(evolutions) { index, evolution ->
            EvolutionCard(evolution = evolution, cardColor = cardColor)

            if (index < evolutions.size - 1) {

                val nextEvolutionCondition = evolutions.getOrNull(index + 1)?.condition

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    nextEvolutionCondition?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next Evolution",
                        tint = Color.Gray,
                    )
                }
            }
        }
    }
}

@Composable
fun EvolutionCard(evolution: EvolutionInfo, cardColor: Color) {
    Card(
        modifier = Modifier
            .height(150.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = 40.dp, y = (-30).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f))
                    .align(Alignment.TopEnd)
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(evolution.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = evolution.name,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )

            Text(
                text = evolution.name.orEmpty(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EvolutionViewPreview() {
    EvolutionView(
        evolutions = listOf(
            EvolutionInfo(
                name = "Bulbasaur",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
            ),
            EvolutionInfo(
                name = "Ivysaur",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
                condition = "Level 16"
            ),
            EvolutionInfo(
                name = "Venusaur",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/3.png",
                condition = "Level 32"
            )
        ),
        cardColor = Color(0xFF4CAF50)
    )
}
