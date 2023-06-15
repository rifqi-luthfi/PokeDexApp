package com.rifqi.myPokeDexApp.ui.home

import androidx.lifecycle.MutableLiveData
import com.rifqi.core.model.PokemonResponse
import com.rifqi.core.repository.PokemonRepository
import com.rifqi.core.util.ApiResponse
import com.rifqi.myPokeDexApp.base.BaseViewModel
import com.rifqi.myPokeDexApp.util.sealedclass.Resource
import com.rifqi.myPokeDexApp.util.threadHandler.setError
import com.rifqi.myPokeDexApp.util.threadHandler.setLoading
import com.rifqi.myPokeDexApp.util.threadHandler.setSuccess

class HomeViewModel (
    private val pokemonRepository: PokemonRepository,
): BaseViewModel()  {
    val pokemonList : MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()

    fun getPokemonList(){
        launchCatchError({
            pokemonList.setLoading()
            when(val response = pokemonRepository.getPokemonList().await()){
                is ApiResponse.Success -> {
                    pokemonList.setSuccess(response.data)
                }
                is ApiResponse.Error -> {
                    pokemonList.setError(Exception(response.exception))
                }
                else -> {
                    pokemonList.setError(Exception("Something went wrong"))
                }
            }
        },{
            pokemonList.setError(it)
        })
    }
}