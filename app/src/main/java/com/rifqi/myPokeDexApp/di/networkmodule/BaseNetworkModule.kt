package com.rifqi.myPokeDexApp.di.networkmodule

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideBaseGsonFactory(): GsonConverterFactory = GsonConverterFactory.create()

fun provideBaseInterceptor() =
    Interceptor { chain ->
        val request = chain
            .request()
            .newBuilder()
            .build()
        chain.proceed(request)
    }


fun provideBaseClient(interceptor: Interceptor, context: Context): OkHttpClient = OkHttpClient.Builder()
    .readTimeout(15, TimeUnit.SECONDS)
    .connectTimeout(15, TimeUnit.SECONDS)
    .addInterceptor(interceptor)
    .addInterceptor(ChuckerInterceptor.Builder(context).build())
    .build()

fun provideBaseRetrofit(
    gson: GsonConverterFactory,
    client: OkHttpClient,
    baseURL: String
): Retrofit = Retrofit.Builder()
    .baseUrl(baseURL)
    .addConverterFactory(gson)
    .client(client)
    .build()