package br.me.vitorcsouza.pokedex.ui.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.me.vitorcsouza.pokedex.domain.model.MoveInfo

@Composable
fun MovesView(
    modifier: Modifier = Modifier,
    moves: List<MoveInfo> = emptyList()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Moves",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        moves.forEach { move ->
            MoveItem(move = move)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovesViewPreview() {
    MovesView(
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
        )
    )
}
