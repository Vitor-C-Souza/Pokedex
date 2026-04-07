package br.me.vitorcsouza.pokedex.data.remote

import br.me.vitorcsouza.pokedex.data.remote.dto.PokemonDetailDto
import br.me.vitorcsouza.pokedex.data.remote.dto.PokemonResponseDto
import br.me.vitorcsouza.pokedex.data.remote.dto.PokemonSpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponseDto

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDetailDto

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: Int
    ): PokemonSpeciesDto

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}
