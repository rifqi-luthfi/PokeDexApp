package com.rifqi.myPokeDexApp.util.threadHandler

import androidx.lifecycle.MutableLiveData
import com.rifqi.myPokeDexApp.base.BaseViewModel.Companion.REMOVE_LOADING
import com.rifqi.myPokeDexApp.base.BaseViewModel.Companion.SHOW_LOADING
import com.rifqi.myPokeDexApp.util.sealedclass.Resource

fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T) {
    postValue(Resource.Success(data))
}

fun <T> MutableLiveData<Resource<T>>.setLoading() {
    postValue(Resource.Loading(null))
}

fun MutableLiveData<Int>.setBaseLoading(isLoading: Boolean) {
    postValue(if(isLoading) SHOW_LOADING else REMOVE_LOADING)
}

fun <T> MutableLiveData<Resource<T>>.setError(ex: Exception) {
    postValue(Resource.Error(ex))
}