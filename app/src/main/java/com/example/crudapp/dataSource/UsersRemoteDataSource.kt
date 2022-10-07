package com.example.crudapp.dataSource

import com.example.crudapp.network.NetworkApiServices
import com.example.crudapp.network.Resource
import com.example.crudapp.network.UserNetworkModel
import com.example.crudapp.network.safeApiCall

class UsersRemoteDataSource(private val service: NetworkApiServices) : UserDataSource {
    override suspend fun fetchUsers(): Resource<ArrayList<UserNetworkModel>> =
        safeApiCall {
            service.getUsers()
        }

    override suspend fun putUser(user: UserNetworkModel): Resource<Unit> =
        safeApiCall {
            service.putUser(user)
        }


}

