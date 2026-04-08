package br.me.vitorcsouza.pokedex.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoveDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("accuracy")
    val accuracy: Int?,
    @SerializedName("power")
    val power: Int?,
    @SerializedName("pp")
    val pp: Int?,
    @SerializedName("type")
    val type: TypeDto,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<MoveFlavorTextDto>
)

data class MoveFlavorTextDto(
    @SerializedName("flavor_text")
    val flavorText: String,
    @SerializedName("language")
    val language: LanguageDto
)
