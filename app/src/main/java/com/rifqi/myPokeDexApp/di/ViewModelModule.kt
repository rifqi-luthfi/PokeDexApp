package com.rifqi.myPokeDexApp.di

import com.rifqi.myPokeDexApp.ui.MainViewModel
import com.rifqi.myPokeDexApp.ui.detail.DetailViewModel
import com.rifqi.myPokeDexApp.ui.home.HomeViewModel
import com.rifqi.myPokeDexApp.ui.mypokemon.MyPokemonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ MainViewModel() }
    viewModel{ HomeViewModel(get()) }
    viewModel{ DetailViewModel(get(), androidContext()) }
    viewModel{ MyPokemonViewModel(androidContext()) }
}