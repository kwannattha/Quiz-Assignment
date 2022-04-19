package com.profile.pokedex.data.repository

import com.profile.pokedex.data.api.APIInterface

class PokemonRepo constructor(private val apiInterface: APIInterface){
    suspend fun getPokemonList() = apiInterface.getPokemons()
}