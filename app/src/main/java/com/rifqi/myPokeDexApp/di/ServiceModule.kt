package com.rifqi.myPokeDexApp.di

import com.rifqi.core.data.remote.PokemonService
import com.rifqi.myPokeDexApp.di.networkmodule.RETROFIT_BASE_URL
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    fun providePokemonService(retrofit: Retrofit) = retrofit.create(PokemonService::class.java)

    single { providePokemonService(get(named(RETROFIT_BASE_URL))) }
}