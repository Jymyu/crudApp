package com.example.crudapp.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
    @SerializedName(DATA)
    var data: T? = null
    internal var error: String? = null
    @Expose
    @SerializedName(SUCCESS)
    val mSuccess = false

    companion object{
        const val SUCCESS = "success"
        const val DATA = "data"
    }
}


