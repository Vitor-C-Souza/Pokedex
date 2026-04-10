package br.me.vitorcsouza.pokedex.ui.presentation.details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.me.vitorcsouza.pokedex.domain.model.MoveInfo
import br.me.vitorcsouza.pokedex.domain.model.PokemonType
import br.me.vitorcsouza.pokedex.ui.components.TypeBadge
import java.util.Locale

@Composable
fun MoveItem(move: MoveInfo = MoveInfo()) {

    val pokemonType = PokemonType.fromString(move.type ?: "")
    val cardColor = pokemonType.color

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(2.dp, cardColor),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = move.name?.replace("-", " ")
                        ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                        ?: "Unknown",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                TypeBadge(
                    type = pokemonType,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(cardColor)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val learnMethod =
                    move.learnMethod?.replace("-", " ")?.replaceFirstChar { it.uppercase() }
                        ?: "N/A"
                val level =
                    if (move.levelLearnedAt != null && move.levelLearnedAt > 0) " (Lv. ${move.levelLearnedAt})" else ""

                Text(
                    text = "Method: $learnMethod$level",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = move.description ?: "No description available",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailsMove(
                    modifier = Modifier.weight(1f),
                    info = move.power?.toString() ?: "N/A",
                    title = "Power"
                )
                DetailsMove(
                    modifier = Modifier.weight(1f),
                    info = move.accuracy?.toString() ?: "N/A",
                    title = "Accuracy",
                    posfixo = "%"
                )
                DetailsMove(
                    modifier = Modifier.weight(1f),
                    info = move.pp?.toString() ?: "N/A",
                    title = "PP"
                )
            }
        }
    }
}

@Composable
fun DetailsMove(
    modifier: Modifier = Modifier,
    info: String = "N/A",
    title: String = "Power",
    posfixo: String = ""
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        Text(
            text = info + if (info != "N/A" && info.isNotEmpty()) posfixo else "",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun MoveItemPreview() {
    MoveItem(
        move = MoveInfo(
            name = "tackle",
            type = "normal",
            description = "Does a quick, low-power punch",
            power = 40,
            accuracy = 100,
            pp = 35,
            learnMethod = "level-up",
            levelLearnedAt = 1
        )
    )
}
