package br.me.vitorcsouza.pokedex.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import br.me.vitorcsouza.pokedex.data.local.Database
import br.me.vitorcsouza.pokedex.data.remote.PokeApi
import br.me.vitorcsouza.pokedex.data.repository.PokemonRepositoryImpl
import br.me.vitorcsouza.pokedex.domain.repository.PokemonRepository
import br.me.vitorcsouza.pokedex.domain.usecase.GetAllPokemon
import br.me.vitorcsouza.pokedex.domain.usecase.GetFavoritePokemon
import br.me.vitorcsouza.pokedex.domain.usecase.GetPokemonByNameOrId
import br.me.vitorcsouza.pokedex.domain.usecase.SearchPokemon
import br.me.vitorcsouza.pokedex.domain.usecase.ToggleFavorite
import br.me.vitorcsouza.pokedex.ui.presentation.details.DetailsViewModel
import br.me.vitorcsouza.pokedex.ui.presentation.home.HomeViewModel
import br.me.vitorcsouza.pokedex.worker.PokemonSyncWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val AppModule = module {
    // Database & DAO
    single {
        Room.databaseBuilder(
            androidApplication(),
            Database::class.java,
            "pokemon_db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
    single { get<Database>().pokemonDao }

    // API
    single {
        Retrofit.Builder()
            .baseUrl(PokeApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApi::class.java)
    }

    // Repository
    single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }

    // UseCases
    factory { GetAllPokemon(get()) }
    factory { SearchPokemon(get()) }
    factory { GetPokemonByNameOrId(get()) }
    factory { ToggleFavorite(get()) }
    factory { GetFavoritePokemon(get()) }

    // Worker
    worker { PokemonSyncWorker(get(), get(), get()) }


    // ViewModels
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { (handle: SavedStateHandle) ->
        DetailsViewModel(get(), get(), handle)
    }
}
