package br.me.vitorcsouza.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.me.vitorcsouza.pokedex.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon ORDER BY id ASC")
    fun getAllPokemon(): Flow<List<PokemonEntity>>

    @Query("""
        SELECT * FROM pokemon 
        WHERE name LIKE '%' || :query || '%' 
        OR types LIKE '%' || :query || '%' 
        OR CAST(id AS TEXT) LIKE '%' || :query || '%'
        ORDER BY id ASC
    """)
    fun searchPokemon(query: String): Flow<List<PokemonEntity>>

    @Query("UPDATE pokemon SET isFavorite = :isFavorite WHERE id = :pokemonId")
    suspend fun updateFavoriteStatus(pokemonId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM pokemon WHERE isFavorite = 1 ORDER BY id ASC")
    fun getFavoritePokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getPokemonCount(): Int

    @Query("DELETE FROM pokemon")
    suspend fun clearAll()
}
