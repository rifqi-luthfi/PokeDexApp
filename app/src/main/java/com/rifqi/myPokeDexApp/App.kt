package com.rifqi.myPokeDexApp

import android.app.Application
import com.rifqi.myPokeDexApp.di.dispatcherModule
import com.rifqi.myPokeDexApp.di.repositoryModule
import com.rifqi.myPokeDexApp.di.serviceModule
import com.rifqi.myPokeDexApp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(listOf(
                viewModelModule,
                serviceModule,
                repositoryModule,
                dispatcherModule
            ))
        }
    }
}

