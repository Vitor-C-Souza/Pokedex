package br.me.vitorcsouza.pokedex.domain.usecase

import br.me.vitorcsouza.pokedex.domain.model.Pokemon
import br.me.vitorcsouza.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SearchPokemon(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(query: String): Flow<List<Pokemon>> {
        // Verifica localmente primeiro
        val localResults = repository.searchPokemon(query).first()
        
        if (localResults.isEmpty() && query.isNotBlank()) {
            // Tenta buscar na API se não houver resultados locais
            repository.catchPokemonByNameOrId(query)
        }
        
        return repository.searchPokemon(query)
    }
}
