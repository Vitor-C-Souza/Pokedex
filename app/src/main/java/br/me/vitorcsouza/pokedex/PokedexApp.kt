package br.me.vitorcsouza.pokedex

import android.app.Application
import br.me.vitorcsouza.pokedex.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PokedexApp)
            modules(AppModule)
        }
    }
}
