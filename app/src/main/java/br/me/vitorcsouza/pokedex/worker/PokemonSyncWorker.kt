package br.me.vitorcsouza.pokedex.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.me.vitorcsouza.pokedex.domain.repository.PokemonRepository

class PokemonSyncWorker(
    context: Context, params: WorkerParameters,
    private val repository: PokemonRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            repository.syncAllPokemons(limit = 1300)
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) Result.retry() else Result.failure()
        }
    }
}