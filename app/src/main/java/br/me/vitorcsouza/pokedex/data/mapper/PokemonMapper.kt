package br.me.vitorcsouza.pokedex.data.mapper

import br.me.vitorcsouza.pokedex.data.local.entity.PokemonEntity
import br.me.vitorcsouza.pokedex.data.remote.dto.PokemonDetailDto
import br.me.vitorcsouza.pokedex.domain.model.EvolutionInfo
import br.me.vitorcsouza.pokedex.domain.model.MoveInfo
import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import kotlinx.serialization.json.Json

fun PokemonDetailDto.toEntity(
    description: String = "",
    isFavorite: Boolean = false,
    moves: List<MoveInfo>? = null,
    evolutions: List<EvolutionInfo>? = null
): PokemonEntity {
    val hp = stats.find { it.stat.name == "hp" }?.baseStat ?: 0
    val attack = stats.find { it.stat.name == "attack" }?.baseStat ?: 0
    val defense = stats.find { it.stat.name == "defense" }?.baseStat ?: 0
    val specialAttack = stats.find { it.stat.name == "special-attack" }?.baseStat ?: 0
    val specialDefense = stats.find { it.stat.name == "special-defense" }?.baseStat ?: 0
    val speed = stats.find { it.stat.name == "speed" }?.baseStat ?: 0

    return PokemonEntity(
        id = id,
        name = name,
        imageUrl = sprites.other?.officialArtwork?.frontDefault ?: "",
        height = height / 10.0,
        weight = weight / 10.0,
        types = types.joinToString(",") { it.type.name },
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed,
        description = description,
        isFavorite = isFavorite,
        movesJson = moves?.let { Json.encodeToString(it) },
        evolutionsJson = evolutions?.let { Json.encodeToString(it) }
    )
}

fun PokemonEntity.toDomain(): Pokemon {
    val moves = movesJson?.let {
        try {
            Json.decodeFromString<List<MoveInfo>>(it)
        } catch (e: Exception) {
            emptyList()
        }
    }
    val evolutions = evolutionsJson?.let {
        try {
            Json.decodeFromString<List<EvolutionInfo>>(it)
        } catch (e: Exception) {
            emptyList()
        }
    }

    return Pokemon(
        id = id,
        name = name,
        imageUrl = imageUrl,
        height = height,
        weight = weight,
        types = types.split(","),
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed,
        description = description,
        isFavorite = isFavorite,
        moves = moves,
        evolutions = evolutions
    )
}

fun PokemonDetailDto.toDomain(): Pokemon {
    val hp = stats.find { it.stat.name == "hp" }?.baseStat ?: 0
    val attack = stats.find { it.stat.name == "attack" }?.baseStat ?: 0
    val defense = stats.find { it.stat.name == "defense" }?.baseStat ?: 0
    val specialAttack = stats.find { it.stat.name == "special-attack" }?.baseStat ?: 0
    val specialDefense = stats.find { it.stat.name == "special-defense" }?.baseStat ?: 0
    val speed = stats.find { it.stat.name == "speed" }?.baseStat ?: 0

    val movesDomain = moves.map { moveEntry ->
        val latestVersionDetail = moveEntry.versionGroupDetails.lastOrNull()
        MoveInfo(
            name = moveEntry.move.name,
            learnMethod = latestVersionDetail?.moveLearnMethod?.name,
            levelLearnedAt = latestVersionDetail?.levelLearnedAt
        )
    }

    return Pokemon(
        id = id,
        name = name,
        imageUrl = sprites.other?.officialArtwork?.frontDefault ?: "",
        height = height / 10.0,
        weight = weight / 10.0,
        types = types.map { it.type.name },
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed,
        moves = movesDomain
    )
}
