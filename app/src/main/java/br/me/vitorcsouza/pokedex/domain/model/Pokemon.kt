package br.me.vitorcsouza.pokedex.domain.model

import androidx.compose.ui.graphics.Color
import br.me.vitorcsouza.pokedex.ui.theme.*
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int
) {
    val formattedId: String get() = "#${id.toString().padStart(3, '0')}"
    
    // Helper to get types with colors
    val pokemonTypes: List<PokemonType> get() = types.map { PokemonType.fromString(it) }
}

enum class PokemonType(val typeName: String, val color: Color) {
    NORMAL("normal", TypeNormal),
    FIRE("fire", TypeFire),
    WATER("water", TypeWater),
    ELECTRIC("electric", TypeElectric),
    GRASS("grass", TypeGrass),
    ICE("ice", TypeIce),
    FIGHTING("fighting", TypeFighting),
    POISON("poison", TypePoison),
    GROUND("ground", TypeGround),
    FLYING("flying", TypeFlying),
    PSYCHIC("psychic", TypePsychic),
    BUG("bug", TypeBug),
    ROCK("rock", TypeRock),
    GHOST("ghost", TypeGhost),
    DRAGON("dragon", TypeDragon),
    DARK("dark", TypeDark),
    STEEL("steel", TypeSteel),
    FAIRY("fairy", TypeFairy),
    UNKNOWN("unknown", TypeUnknown);

    companion object {
        fun fromString(type: String): PokemonType {
            return entries.find { it.typeName.equals(type, ignoreCase = true) } ?: UNKNOWN
        }
    }
}
