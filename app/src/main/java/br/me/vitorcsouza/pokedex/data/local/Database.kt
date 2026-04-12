package br.me.vitorcsouza.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.me.vitorcsouza.pokedex.data.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 5, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}