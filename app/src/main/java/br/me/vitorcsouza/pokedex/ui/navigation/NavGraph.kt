package br.me.vitorcsouza.pokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.ui.presentation.details.DetailsScreen
import br.me.vitorcsouza.pokedex.ui.presentation.home.HomeScreen
import br.me.vitorcsouza.pokedex.ui.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object Splash : Screen()

    @Serializable
    object Home : Screen()

    @Serializable
    data class Details(val pokemonName: String) : Screen()
}


@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            SplashScreen(
                onFinished = {
                    navController.navigate(Screen.Home) {
                        popUpTo(Screen.Splash) { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.Home> {
            HomeScreen(
                onPokemonClick = { pokemon: Pokemon ->
                    pokemon.name?.let { pokemonName ->
                        navController.navigate(
                            Screen.Details(
                                pokemonName = pokemonName
                            )
                        )
                    }
                }
            )
        }

        composable<Screen.Details> {
            DetailsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

