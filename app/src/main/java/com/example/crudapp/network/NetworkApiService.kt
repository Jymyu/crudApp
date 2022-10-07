package com.example.crudapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

private const val BASE_URL = "https://hello-world.innocv.com/"

private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()


interface NetworkApiServices {

    @GET("api/User/")
    suspend fun getUsers():
            Response<ArrayList<UserNetworkModel>>

    @PUT("api/User/")
    suspend fun putUser(@Body user: UserNetworkModel) :Response<Unit>

}

object UsersApi {
    val retrofitService: NetworkApiServices by lazy {
        retrofit.create(
                NetworkApiServices::class.java
        )
    }
}