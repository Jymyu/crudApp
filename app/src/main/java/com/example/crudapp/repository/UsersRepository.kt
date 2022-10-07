package com.example.crudapp.repository

import com.example.crudapp.dataSource.UserDataSource
import com.example.crudapp.network.Resource
import com.example.crudapp.network.UserNetworkModel
import com.example.crudapp.network.performRequestData
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val usersDataSource: UserDataSource) : UsersRepositoryContract {

    override suspend fun fetchUsers(): Flow<Resource<ArrayList<UserNetworkModel>>> = performRequestData {
        usersDataSource.fetchUsers()
    }

    override suspend fun putUser(user: UserNetworkModel)  = performRequestData {
        usersDataSource.putUser(user)
    }
}
