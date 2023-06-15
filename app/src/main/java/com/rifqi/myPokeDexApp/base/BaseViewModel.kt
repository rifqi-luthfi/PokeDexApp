package com.rifqi.myPokeDexApp.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class BaseViewModel : ViewModel(){
    companion object {
        const val SHOW_LOADING : Int = 1
        const val REMOVE_LOADING : Int = 2
    }
    val loading : MutableLiveData<Int> = MutableLiveData()

    protected fun launchCatchError(
        block: suspend () -> Unit,
        catch: suspend (e: Exception) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                block()
            }
            catch (e: Exception) {
                catch(e)
            }
        }
    }
}