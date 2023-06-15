package com.rifqi.myPokeDexApp.di

import com.rifqi.myPokeDexApp.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ MainViewModel() }
}