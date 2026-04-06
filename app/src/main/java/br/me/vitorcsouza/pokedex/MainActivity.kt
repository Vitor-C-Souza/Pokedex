package br.me.vitorcsouza.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import br.me.vitorcsouza.pokedex.ui.presentation.appicon.AppIcon
import br.me.vitorcsouza.pokedex.ui.presentation.home.HomeScreen
import br.me.vitorcsouza.pokedex.ui.presentation.splash.SplashScreen
import br.me.vitorcsouza.pokedex.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showSplash by remember { mutableStateOf(true) }
            
            PokedexTheme(darkTheme = false) {
                if (showSplash) {
                    SplashScreen(onFinished = { showSplash = false })
                } else {
                    HomeScreen()
                }
            }
        }
    }
}
