package br.me.vitorcsouza.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Double,
    val weight: Double,
    val types: String,
    val description: String = "",
    val isFavorite: Boolean = false,

    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,

    val movesJson: String? = null,
    val evolutionsJson: String? = null
)
