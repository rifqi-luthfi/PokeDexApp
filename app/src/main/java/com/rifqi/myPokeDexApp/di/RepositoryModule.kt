package com.rifqi.myPokeDexApp.di

import com.rifqi.core.repository.PokemonRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PokemonRepository(get()) }
}