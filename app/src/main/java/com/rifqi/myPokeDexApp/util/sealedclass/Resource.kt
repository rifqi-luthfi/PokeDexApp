package com.rifqi.myPokeDexApp.util.sealedclass

sealed class Resource<T> {
    data class Success<T>(val data: T): Resource<T>()
    data class Error<Nothing>(val exception: Exception): Resource<Nothing>()
    data class Loading<T>(val data: T?): Resource<T>()

    fun isSuccess(): Boolean = this is Success

    fun get(): T = (this as Success).data
}