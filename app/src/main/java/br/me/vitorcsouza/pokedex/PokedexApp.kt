package br.me.vitorcsouza.pokedex

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import br.me.vitorcsouza.pokedex.di.AppModule
import br.me.vitorcsouza.pokedex.worker.PokemonSyncWorker
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class PokedexApp : Application(), Configuration.Provider {

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(getKoin().get<org.koin.androidx.workmanager.factory.KoinWorkerFactory>())
            .build()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PokedexApp)
            workManagerFactory()
            modules(AppModule)
        }

        setupPokemonSync()
    }

    private fun setupPokemonSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<PokemonSyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "PokemonDatabaseSync",
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }
}
