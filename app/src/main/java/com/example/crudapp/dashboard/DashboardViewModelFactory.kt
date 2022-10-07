package com.example.crudapp.dashboard

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.crudapp.dataSource.UsersRemoteDataSource
import com.example.crudapp.network.UsersApi
import com.example.crudapp.repository.UsersRepository
import com.example.crudapp.useCases.UsersUseCase

class DashboardViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(DashboardViewModel::class.java) -> dashboardViewModelProvider()
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    private fun dashboardViewModelProvider(): DashboardViewModel {
//        val storeRatingRepository = StoreRatingRepository(StoreRatingLocalDataSource(AigDataStore.getInstance(ContextProvider.getApplicationContext())))
        return DashboardViewModel(
            UsersUseCase(UsersRepository(UsersRemoteDataSource(UsersApi.retrofitService)))
        )
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelProvider.NewInstanceFactory? = null

        fun getInstance() =
            INSTANCE ?: synchronized(DashboardViewModelFactory::class.java) {
                INSTANCE ?: DashboardViewModelFactory().also { INSTANCE = it }
            }

        @VisibleForTesting
        fun setInstance(factory: ViewModelProvider.NewInstanceFactory?) {
            INSTANCE = factory
        }
    }
}
