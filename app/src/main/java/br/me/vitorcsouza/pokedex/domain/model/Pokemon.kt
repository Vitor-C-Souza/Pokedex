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
    val id: Int? = null,
    val name: String? = null,
    val imageUrl: String? = null,
    val height: Double? = null,
    val weight: Double? = null,
    val types: List<String>? = null,
    val hp: Int? = null,
    val attack: Int? = null,
    val defense: Int? = null,
    val specialAttack: Int? = null,
    val specialDefense: Int? = null,
    val speed: Int? = null,
    val description: String? = null,
    val isFavorite: Boolean? = false,
    val moves: List<MoveInfo>? = emptyList(),
    val evolutions: List<EvolutionInfo>? = emptyList()
) {
    val formattedId: String get() = "#${id.toString().padStart(3, '0')}"

    val pokemonTypes: List<PokemonType> get() = types?.map { PokemonType.fromString(it) }.orEmpty()
}

@Serializable
data class EvolutionInfo(
    val name: String? = null,
    val imageUrl: String? = null
)

@Serializable
data class MoveInfo(
    val name: String? = null,
    val type: String? = null,
    val description: String? = null,
    val power: Int? = null,
    val accuracy: Int? = null,
    val pp: Int? = null
)

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
