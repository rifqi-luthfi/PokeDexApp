package com.rifqi.core.repository

import com.rifqi.core.base.BaseRepository
import com.rifqi.core.data.remote.PokemonService
import com.rifqi.core.model.PokemonResponse
import com.rifqi.core.util.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class PokemonRepository(
    private val pokemonService: PokemonService
) : BaseRepository(){

    fun getPokemonList(): Deferred<ApiResponse<PokemonResponse>> {
        return CoroutineScope(Dispatchers.IO).async {
            lateinit var response: PokemonResponse
            try {
                response = execute(pokemonService.getPokemonList())
                ApiResponse.Success(response)
            } catch (ex: Exception) {
                ApiResponse.Error(ex)
            }
        }
    }

}