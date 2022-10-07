package com.example.crudapp.model

import com.example.crudapp.network.UserNetworkModel

data class User(
    val id:Int? = null,
    val name:String?,
    val birthDate:String?,
)

fun User.asNetworkModel() = UserNetworkModel(id,name,birthDate)
