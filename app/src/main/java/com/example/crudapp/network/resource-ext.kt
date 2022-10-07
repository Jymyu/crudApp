package com.example.crudapp.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun <T> performRequestData(
    networkCall: suspend () -> Resource<T>
    ): Flow<Resource<T>> =
    flow {
        emit(Resource.loading(null) as Resource<T>)
        val responseStatus = networkCall.invoke()
        emit(responseStatus)
    }