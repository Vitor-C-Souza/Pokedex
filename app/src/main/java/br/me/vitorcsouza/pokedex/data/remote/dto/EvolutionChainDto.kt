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
    val evolvesTo: List<ChainLinkDto>
)

data class SpeciesDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
