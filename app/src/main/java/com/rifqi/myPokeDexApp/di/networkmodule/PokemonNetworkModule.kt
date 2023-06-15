package com.rifqi.myPokeDexApp.di.networkmodule

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val RETROFIT_BASE_URL = "retrofit_base_url"
const val INTERCEPTOR_URL = "interceptor_url"
const val CLIENT_URL = "client_url"
const val GSON_URL = "gson_url"

val pokemonNetworkModule = module {

    single(named(INTERCEPTOR_URL)) { provideBaseInterceptor() }
    single(named(GSON_URL)) { provideBaseGsonFactory() }
    single(named(CLIENT_URL)) { provideBaseClient(get(named(INTERCEPTOR_URL)), androidContext()) }

    single(named(RETROFIT_BASE_URL)) {
        provideBaseRetrofit(
            gson = get(named(GSON_URL)),
            client = get(named(CLIENT_URL)),
            baseURL = "https://pokeapi.co/api/v2/"
        )
    }
}