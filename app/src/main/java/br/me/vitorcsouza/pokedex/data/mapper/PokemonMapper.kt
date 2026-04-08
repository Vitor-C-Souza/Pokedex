package br.me.vitorcsouza.pokedex.data.mapper

import br.me.vitorcsouza.pokedex.data.local.entity.PokemonEntity
import br.me.vitorcsouza.pokedex.data.remote.dto.PokemonDetailDto
import br.me.vitorcsouza.pokedex.domain.model.Pokemon

fun PokemonDetailDto.toEntity(
    description: String = "",
    isFavorite: Boolean = false
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
        isFavorite = isFavorite
    )
}

fun PokemonEntity.toDomain(): Pokemon {
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
        isFavorite = isFavorite
    )
}

fun PokemonDetailDto.toDomain(): Pokemon {
    val hp = stats.find { it.stat.name == "hp" }?.baseStat ?: 0
    val attack = stats.find { it.stat.name == "attack" }?.baseStat ?: 0
    val defense = stats.find { it.stat.name == "defense" }?.baseStat ?: 0
    val specialAttack = stats.find { it.stat.name == "special-attack" }?.baseStat ?: 0
    val specialDefense = stats.find { it.stat.name == "special-defense" }?.baseStat ?: 0
    val speed = stats.find { it.stat.name == "speed" }?.baseStat ?: 0

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
        speed = speed
    )
}
