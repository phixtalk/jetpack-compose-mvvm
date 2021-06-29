package com.mvvmcompose.data.remote

import com.mvvmcompose.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceBuilder {
//    fun providePokemonRepository(
//        api: PokeApi
//    ) = PokemonRepository(api)

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okkHttpclient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    /*
    * Using the GsonConverterFactory we can map
    * the json object response we get from the web api
    * to a kotlin class
    * */
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .client(okkHttpclient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }
}