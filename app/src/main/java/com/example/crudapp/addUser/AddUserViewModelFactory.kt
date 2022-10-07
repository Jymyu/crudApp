package com.example.crudapp.addUser

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crudapp.dataSource.UsersRemoteDataSource
import com.example.crudapp.network.UsersApi
import com.example.crudapp.repository.UsersRepository
import com.example.crudapp.useCases.UsersUseCase

class AddUserViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(AddUserViewModel::class.java) -> addUserViewModelProvider()
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    private fun addUserViewModelProvider(): AddUserViewModel {
        return AddUserViewModel(
            UsersUseCase(UsersRepository(UsersRemoteDataSource(UsersApi.retrofitService)))
        )
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelProvider.NewInstanceFactory? = null

        fun getInstance() =
            INSTANCE ?: synchronized(AddUserViewModelFactory::class.java) {
                INSTANCE ?: AddUserViewModelFactory().also { INSTANCE = it }
            }

        @VisibleForTesting
        fun setInstance(factory: ViewModelProvider.NewInstanceFactory?) {
            INSTANCE = factory
        }
    }
}
