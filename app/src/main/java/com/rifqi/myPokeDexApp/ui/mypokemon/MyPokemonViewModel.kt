package com.rifqi.myPokeDexApp.ui.mypokemon

import android.content.Context
import androidx.lifecycle.LiveData
import com.rifqi.core.data.local.entity.PokemonEntity
import com.rifqi.core.data.local.room.PokemonDao
import com.rifqi.core.data.local.room.PokemonDatabase
import com.rifqi.myPokeDexApp.base.BaseViewModel

class MyPokemonViewModel (
    private val appContext: Context,
): BaseViewModel()  {
    private var pokemonDao: PokemonDao?
    private var pokemonDb: PokemonDatabase?

    init {
        pokemonDb = PokemonDatabase.getDatabase(appContext)
        pokemonDao = pokemonDb?.pokemonDao()
    }

    fun getMyPokemonList(): LiveData<List<PokemonEntity>>? {
        return pokemonDao?.getMyList()
    }
}