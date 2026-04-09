package br.me.vitorcsouza.pokedex.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EvolutionChainDto(
    @SerializedName("chain")
    val chain: ChainLinkDto
)

data class ChainLinkDto(
    @SerializedName("species")
    val species: SpeciesDto,
    @SerializedName("evolves_to")
    val evolvesTo: List<ChainLinkDto>,
    @SerializedName("evolution_details")
    val evolutionDetails: List<EvolutionDetailDto>
)

data class EvolutionDetailDto(
    @SerializedName("min_level")
    val minLevel: Int?,
    @SerializedName("item")
    val item: SpeciesDto?,
    @SerializedName("trigger")
    val trigger: SpeciesDto?,
    @SerializedName("held_item")
    val heldItem: SpeciesDto?,
    @SerializedName("known_move")
    val knownMove: SpeciesDto?,
    @SerializedName("known_move_type")
    val knownMoveType: SpeciesDto?,
    @SerializedName("location")
    val location: SpeciesDto?,
    @SerializedName("min_happiness")
    val minHappiness: Int?,
    @SerializedName("min_beauty")
    val minBeauty: Int?,
    @SerializedName("min_affection")
    val minAffection: Int?,
    @SerializedName("needs_overworld_rain")
    val needsOverworldRain: Boolean?,
    @SerializedName("party_species")
    val partySpecies: SpeciesDto?,
    @SerializedName("party_type")
    val partyType: SpeciesDto?,
    @SerializedName("relative_physical_stats")
    val relativePhysicalStats: Int?,
    @SerializedName("time_of_day")
    val timeOfDay: String?,
    @SerializedName("trade_species")
    val tradeSpecies: SpeciesDto?,
    @SerializedName("turn_upside_down")
    val turnUpsideDown: Boolean?
)

data class SpeciesDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
