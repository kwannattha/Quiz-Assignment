package com.profile.pokedex.data.api

import com.profile.pokedex.data.api.model.PokemonAPIResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

interface APIInterface {


        @GET("pokemon/")
       suspend fun getPokemons() : Response<PokemonAPIResult>

        companion object {
            var BASE_URL = "https://pokeapi.co/api/v2"
            fun create() : APIInterface {
                var retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

                return retrofit.create(APIInterface::class.java)
            }
        }
    }