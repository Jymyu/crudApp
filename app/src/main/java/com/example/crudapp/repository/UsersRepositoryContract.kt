package com.example.crudapp.repository

import com.example.crudapp.model.User
import com.example.crudapp.network.Resource
import com.example.crudapp.network.UserNetworkModel
import kotlinx.coroutines.flow.Flow

interface UsersRepositoryContract {

    suspend fun fetchUsers(): Flow<Resource<ArrayList<UserNetworkModel>>>

    suspend fun putUser(user:UserNetworkModel) : Flow<Resource<Unit>>
}
