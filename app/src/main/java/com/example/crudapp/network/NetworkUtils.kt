package com.example.crudapp.network

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> safeApiCall(
    call: suspend () -> Response<T>
): Resource<T> {
    return try {
        val response =  call.invoke()
        if (response.isSuccessful) {
           Resource.success(response.body(),response.code())
        } else {
            error(" ${response.code()} ${response.message()}",response.code())
        }
    } catch (throwable: Throwable) {
        return error(throwable.message ?: throwable.toString(),500)
    }
}

private fun <T> error(message: String, responseCode: Int): Resource<T> {
    Log.e("remoteDataSource", message)
    return Resource.error("Network call has failed for a following reason: $message",responseCode)
}