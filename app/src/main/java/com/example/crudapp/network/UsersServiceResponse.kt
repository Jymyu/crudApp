package com.example.crudapp.network

import com.google.gson.annotations.SerializedName



data class UserNetworkModel(
        val id:Int? = null,
        val name:String?,
        val birthdate:String?,
        )

//fun PhoneNetwork.asDatabaseModel(): PhonesEntity {
//    return PhonesEntity(sku.toString(),brand,name,specialPrice.toString(),discount.toString(),price.toString(),rating,imageUrl)
//}
