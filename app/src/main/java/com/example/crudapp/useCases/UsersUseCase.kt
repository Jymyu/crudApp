package com.example.crudapp.useCases

import com.example.crudapp.network.UserNetworkModel
import com.example.crudapp.repository.UsersRepositoryContract

class UsersUseCase(private val repository: UsersRepositoryContract) {
    suspend fun getUsers() = repository.fetchUsers()
    suspend fun putUser(user: UserNetworkModel) = repository.putUser(user)
}

