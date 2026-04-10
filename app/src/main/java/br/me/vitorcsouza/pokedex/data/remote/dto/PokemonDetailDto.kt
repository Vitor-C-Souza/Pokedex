package br.me.vitorcsouza.pokedex.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Double,
    @SerializedName("weight")
    val weight: Double,
    @SerializedName("types")
    val types: List<TypeEntryDto>,
    @SerializedName("stats")
    val stats: List<StatEntryDto>,
    @SerializedName("sprites")
    val sprites: SpritesDto,
    @SerializedName("moves")
    val moves: List<MoveEntryDto>
)

data class MoveEntryDto(
    @SerializedName("move")
    val move: MoveDto,
    @SerializedName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetailDto>
)

data class VersionGroupDetailDto(
    @SerializedName("level_learned_at")
    val levelLearnedAt: Int,
    @SerializedName("move_learn_method")
    val moveLearnMethod: MoveLearnMethodDto,
    @SerializedName("version_group")
    val versionGroup: VersionGroupDto
)

data class MoveLearnMethodDto(
    @SerializedName("name")
    val name: String
)

data class VersionGroupDto(
    @SerializedName("name")
    val name: String
)

data class MoveDto(
    @SerializedName("name")
    val name: String
)

data class TypeEntryDto(
    @SerializedName("type")
    val type: TypeDto
)

data class TypeDto(
    @SerializedName("name")
    val name: String
)

data class StatEntryDto(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("stat")
    val stat: StatDto
)

data class StatDto(
    @SerializedName("name")
    val name: String
)

data class SpritesDto(
    @SerializedName("other")
    val other: OtherSpritesDto?
)

data class OtherSpritesDto(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtworkDto?
)

data class OfficialArtworkDto(
    @SerializedName("front_default")
    val frontDefault: String?
)
