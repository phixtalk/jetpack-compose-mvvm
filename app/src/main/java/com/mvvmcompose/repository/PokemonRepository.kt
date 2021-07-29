package com.mvvmcompose.repository

import com.mvvmcompose.data.remote.responses.Pokemon
import com.mvvmcompose.data.remote.PokeApi
import com.mvvmcompose.data.remote.responses.PokemonList
import com.mvvmcompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
): BaseRepository() {
    suspend fun getPokenList(limit: Int, offset: Int): Resource<PokemonList> {
        return safeApiCall { api.getPokemonList(limit, offset) }
    }

    suspend fun getPokenInfo(pokemonName: String): Resource<Pokemon>{
        return safeApiCall { api.getPokenmonInfo(pokemonName) }
    }
}