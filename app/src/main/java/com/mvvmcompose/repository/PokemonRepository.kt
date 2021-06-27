package com.mvvmcompose.repository

import com.mvvmcompose.data.remote.responses.Pokemon
import com.mvvmcompose.data.remote.PokeApi
import com.mvvmcompose.data.remote.responses.PokemonList
import com.mvvmcompose.util.Resource

class PokemonRepository (
    private val api: PokeApi
) {
    suspend fun getPokenList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        }catch (e: Exception){
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    suspend fun getPokenInfo(pokemonName: String): Resource<Pokemon>{
        val response = try {
            api.getPokenmonInfo(pokemonName)
        }catch (e: Exception){
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

}