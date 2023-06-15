package com.rifqi.myPokeDexApp.ui.detail

import androidx.lifecycle.MutableLiveData
import com.rifqi.core.model.DetailPokemonResponse
import com.rifqi.core.repository.PokemonRepository
import com.rifqi.core.util.ApiResponse
import com.rifqi.myPokeDexApp.base.BaseViewModel
import com.rifqi.myPokeDexApp.util.sealedclass.Resource
import com.rifqi.myPokeDexApp.util.threadHandler.setError
import com.rifqi.myPokeDexApp.util.threadHandler.setLoading
import com.rifqi.myPokeDexApp.util.threadHandler.setSuccess

class DetailViewModel (
    private val pokemonRepository: PokemonRepository,
): BaseViewModel()  {
    val pokemonDetailList : MutableLiveData<Resource<DetailPokemonResponse>> = MutableLiveData()

    fun getPokemonDetail(name: String){
        launchCatchError({
            pokemonDetailList.setLoading()
            when(val response = pokemonRepository.getPokemonDetail(name).await()){
                is ApiResponse.Success -> {
                    pokemonDetailList.setSuccess(response.data)
                }
                is ApiResponse.Error -> {
                    pokemonDetailList.setError(Exception(response.exception))
                }
                else -> {
                    pokemonDetailList.setError(Exception("Something went wrong"))
                }
            }
        },{
            pokemonDetailList.setError(it)
        })
    }

}