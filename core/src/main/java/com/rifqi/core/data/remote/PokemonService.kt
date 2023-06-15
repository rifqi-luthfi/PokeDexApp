package com.rifqi.core.data.remote

import com.rifqi.core.model.DetailPokemonResponse
import com.rifqi.core.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon")
    fun getPokemonList(): Call<PokemonResponse>

    @GET("pokemon/{name}")
    fun getPokemonDetail(
        @Path("name") name : String
    ): Call<DetailPokemonResponse>
}