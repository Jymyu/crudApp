package com.example.crudapp.dataSource

import com.example.crudapp.network.BaseResponse
import com.example.crudapp.network.Resource
import com.example.crudapp.network.UserNetworkModel
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun fetchUsers(): Resource<ArrayList<UserNetworkModel>>
    suspend fun putUser(user:UserNetworkModel) : Resource<Unit>
}
