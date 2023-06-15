package com.rifqi.core.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rifqi.core.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {
    @Insert
    fun addToMyList(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM pokemonEntity")
    fun getMyList(): LiveData<List<PokemonEntity>>

    @Query("DELETE FROM pokemonEntity WHERE pokemonEntity.name = :name")
    fun removeFromMyList(name: String)

    @Query("SELECT count(*) FROM pokemonEntity WHERE pokemonEntity.id = :id")
    fun checkList(id: Int): Int
}