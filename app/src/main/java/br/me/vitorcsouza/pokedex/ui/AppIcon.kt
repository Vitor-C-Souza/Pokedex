package br.me.vitorcsouza.pokedex.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFFF3F4F6), Color(0xFFE5E7EB))
                )
            )
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            // Shadow under the icon
            Box(
                modifier = Modifier
                    .offset(y = 8.dp)
                    .size(width = 128.dp, height = 16.dp)
                    .blur(16.dp)
                    .background(Color.Black.copy(alpha = 0.2f), CircleShape)
            )

            // Main Icon Container
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFEF4444), // red-500
                                Color(0xFFF97316), // orange-500
                                Color(0xFFEAB308)  // yellow-500
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Background decorations
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Top-right light circle
                    Box(
                        modifier = Modifier
                            .offset(x = 32.dp, y = (-32).dp)
                            .size(96.dp)
                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                            .align(Alignment.TopEnd)
                    )
                    // Bottom-left dark circle
                    Box(
                        modifier = Modifier
                            .offset(x = (-48).dp, y = 48.dp)
                            .size(128.dp)
                            .background(Color.Black.copy(alpha = 0.05f), CircleShape)
                            .align(Alignment.BottomStart)
                    )
                }

                // Pokéball Design Container
                Box(
                    modifier = Modifier
                        .size(112.dp)
                        .background(Color.White, CircleShape)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Pokéball Inner Circle with Border
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(width = 10.dp, color = Color(0xFFDC2626), shape = CircleShape) // red-600
                            .clip(CircleShape)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Top Half
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .background(Color(0xFFDC2626)) // red-600
                            )
                            // Bottom Half
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .background(Color.White)
                            )
                        }

                        // Middle Line
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(Color(0xFF111827)) // gray-900
                                .align(Alignment.Center)
                        )

                        // Center Button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                                .border(width = 4.dp, color = Color(0xFF111827), shape = CircleShape)
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(Color(0xFF111827), CircleShape)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppIconPreview() {
    AppIcon(modifier = Modifier.size(300.dp))
}
