package br.me.vitorcsouza.pokedex.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.ui.presentation.details.DetailsScreen
import br.me.vitorcsouza.pokedex.ui.presentation.home.HomeScreen
import br.me.vitorcsouza.pokedex.ui.presentation.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Details : Screen("details/{pokemonName}") {
        fun createRoute(pokemonName: String) = "details/$pokemonName"
    }
}


@Composable
fun NavGraph(navController: NavHostController) {

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
                }
            )
        }
    }
}

