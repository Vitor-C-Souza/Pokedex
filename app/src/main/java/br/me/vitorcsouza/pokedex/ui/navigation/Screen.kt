package br.me.vitorcsouza.pokedex.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Details : Screen("details/{pokemonName}") {
        fun createRoute(pokemonName: String) = "details/$pokemonName"
    }
}
