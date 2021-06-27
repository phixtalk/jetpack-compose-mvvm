package com.mvvmcompose.pokemondetail

import androidx.lifecycle.ViewModel
import com.mvvmcompose.data.remote.responses.Pokemon
import com.mvvmcompose.repository.PokemonRepository
import com.mvvmcompose.util.Resource


class PokemonDetailViewModel (
    private val repository: PokemonRepository
): ViewModel() {

        suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
            return repository.getPokenInfo(pokemonName)
        }
}