package br.me.vitorcsouza.pokedex.domain.model

import androidx.compose.ui.graphics.Color
import br.me.vitorcsouza.pokedex.ui.theme.TypeBug
import br.me.vitorcsouza.pokedex.ui.theme.TypeDark
import br.me.vitorcsouza.pokedex.ui.theme.TypeDragon
import br.me.vitorcsouza.pokedex.ui.theme.TypeElectric
import br.me.vitorcsouza.pokedex.ui.theme.TypeFairy
import br.me.vitorcsouza.pokedex.ui.theme.TypeFighting
import br.me.vitorcsouza.pokedex.ui.theme.TypeFire
import br.me.vitorcsouza.pokedex.ui.theme.TypeFlying
import br.me.vitorcsouza.pokedex.ui.theme.TypeGhost
import br.me.vitorcsouza.pokedex.ui.theme.TypeGrass
import br.me.vitorcsouza.pokedex.ui.theme.TypeGround
import br.me.vitorcsouza.pokedex.ui.theme.TypeIce
import br.me.vitorcsouza.pokedex.ui.theme.TypeNormal
import br.me.vitorcsouza.pokedex.ui.theme.TypePoison
import br.me.vitorcsouza.pokedex.ui.theme.TypePsychic
import br.me.vitorcsouza.pokedex.ui.theme.TypeRock
import br.me.vitorcsouza.pokedex.ui.theme.TypeSteel
import br.me.vitorcsouza.pokedex.ui.theme.TypeUnknown
import br.me.vitorcsouza.pokedex.ui.theme.TypeWater
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
    val speed: Int,
    val description: String = ""
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
