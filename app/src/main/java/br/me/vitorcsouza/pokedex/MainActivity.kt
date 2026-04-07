package br.me.vitorcsouza.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.ui.navigation.Screen
import br.me.vitorcsouza.pokedex.ui.presentation.details.DetailsScreen
import br.me.vitorcsouza.pokedex.ui.presentation.home.HomeScreen
import br.me.vitorcsouza.pokedex.ui.presentation.splash.SplashScreen
import br.me.vitorcsouza.pokedex.ui.theme.PokedexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()
        setContent {
            PokedexTheme(darkTheme = false) {
                PokedexNavHost()
            }
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}

@Composable
fun PokedexNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onPokemonClick = { pokemon: Pokemon ->
                    navController.navigate(Screen.Details.createRoute(pokemon.name))
                }
            )
        }

        composable(Screen.Details.route) {
            DetailsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onFavoriteClick = {
                    // TODO: Implement favorite
                }
            )
        }
    }
}
