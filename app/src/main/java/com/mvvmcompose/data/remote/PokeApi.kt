package com.mvvmcompose.data.remote

import com.mvvmcompose.data.remote.responses.Pokemon
import com.mvvmcompose.data.remote.responses.PokemonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    /*
    We can configure the interface methods to return the responses
    in different shapes. eg we can return Call<T>, Response<T> or just T
    Where T is the Kotlin class mapped from the json response object
    We will return Response<T> because we want to access some methods (eg, isSuccessful)
    in our base repository, where we can handle all network requests and errors
    in a single location, rather than having multiple try-catch blocks in different repositories
    */
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Response<PokemonList>

    @GET("pokemon/{name}")
    suspend fun getPokenmonInfo(
        @Path("name") name: String
    ): Response<Pokemon>
}