package com.rifqi.myPokeDexApp.ui.detail

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.lifecycle.MutableLiveData
import com.rifqi.core.data.local.entity.PokemonEntity
import com.rifqi.core.data.local.room.PokemonDao
import com.rifqi.core.data.local.room.PokemonDatabase
import com.rifqi.core.model.DetailPokemonResponse
import com.rifqi.core.repository.PokemonRepository
import com.rifqi.core.util.ApiResponse
import com.rifqi.myPokeDexApp.base.BaseViewModel
import com.rifqi.myPokeDexApp.util.sealedclass.Resource
import com.rifqi.myPokeDexApp.util.threadHandler.setError
import com.rifqi.myPokeDexApp.util.threadHandler.setLoading
import com.rifqi.myPokeDexApp.util.threadHandler.setSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel (
    private val pokemonRepository: PokemonRepository,
    private val appContext: Context
): BaseViewModel()  {
    val pokemonDetailList : MutableLiveData<Resource<DetailPokemonResponse>> = MutableLiveData()
    private var pokemonDao: PokemonDao?
    private var pokemonDb: PokemonDatabase?
    var count = 0

    init {
        pokemonDb = PokemonDatabase.getDatabase(appContext)
        pokemonDao = pokemonDb?.pokemonDao()
    }

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

    fun addToMyPokemonList(id: Int, name: String, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemon = PokemonEntity(id, name, url)
                pokemonDao?.addToMyList(pokemon)
            } catch (e: SQLiteConstraintException) {
                Toast.makeText(appContext, "Pokemon with the same ID already exists in MyList.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkPokemon(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            count = pokemonDao?.checkList(id) ?: 0
        }
    }

}