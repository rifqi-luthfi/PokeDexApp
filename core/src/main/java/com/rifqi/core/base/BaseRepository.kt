package com.rifqi.core.base

import android.util.Log
import com.google.gson.Gson
import com.rifqi.core.util.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

abstract class BaseRepository{

    protected fun<T> execute(call : Call<T>) : T {
        try{
            val response = call.execute()
            return when(response.isSuccessful){
                true -> {
                    Log.d("<RES>", "Url: ${call.request().url} | data: ${response.message()}")
                    response.body()!!
                }
                false -> {
                    Log.e("<RES>", "Url: ${call.request().url} | Reason: ${response.message()}")
                    throw Exception()
                }
            }
        }
        catch (e : Exception){
            e.message?.let {
                Log.e("<RES>", "Url: ${call.request().url} | Reason: $it")
            }
            throw e
        }
    }

    protected suspend fun<T> executeWithCatch(dispatchers: CoroutineDispatcher = Dispatchers.IO, call : Call<T>, requestName: String = "", request: Any? = null) : ApiResponse<T> {
        return withContext(dispatchers) {
            if(requestName.isNotEmpty() && request != null) {
                Log.d("<REQ>", "Url: ${call.request().url} | data: $requestName : $request")
            }

            try{
                val response = call.execute()
                when(response.isSuccessful){
                    true -> {
                        val responseJson = Gson().toJson(response.body()!!)
                        val message = if(requestName.isNotEmpty()) "$requestName : $responseJson"
                        else responseJson
                        Log.d("<RES>", "Url: ${call.request().url} | data: $message")

                        ApiResponse.Success(response.body()!!)
                    }
                    false -> {
                        val responseJson = response.message()
                        val message = if(requestName.isNotEmpty()) "$requestName : $responseJson"
                        else responseJson
                        Log.e("<RES>", "Url: ${call.request().url} | Reason: $message")

                        ApiResponse.Error(Exception("Something went wrong"))
                    }
                }
            }
            catch (e : Exception){
                val responseJson = e.message ?: "Something went wrong"
                val message = if(requestName.isNotEmpty()) "$requestName : $responseJson"
                else responseJson

                Log.e("<RES>", "Url: ${call.request().url} | Reason: $message")
                ApiResponse.Error(Exception("Something went wrong"))
            }
        }
    }
}

