package com.example.crudapp.dashboard

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudapp.dashboard.DashboardContract.State.*
import com.example.crudapp.model.User
import com.example.crudapp.model.asNetworkModel
import com.example.crudapp.network.Status
import com.example.crudapp.network.UserNetworkModel
import com.example.crudapp.useCases.UsersUseCase
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val usersUseCase: UsersUseCase,
) : ViewModel(), DashboardContract.ViewModel {


    override val dashboardState = MediatorLiveData<DashboardContract.State>()
    override val dashboardEvent = MediatorLiveData<DashboardContract.Event>()

    override fun invokeAction(action: DashboardContract.Action) {
        when(action){
            DashboardContract.Action.FetchUsers -> fetchUsers()
            is DashboardContract.Action.PutUser -> putUser(action.user)
        }
    }

    private fun putUser(user: User?) {
        user?.let {
            viewModelScope.launch {
                usersUseCase.putUser(
                    it.asNetworkModel()
                ).collect{resource ->
                    when (resource.status) {
                        Status.SUCCESS -> dashboardState.postValue(SuccessMessage(resource.message))
                        Status.LOADING -> dashboardState.postValue(Loading)
                        Status.ERROR -> dashboardState.postValue(Error(resource))
                    }

                }
            }
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            usersUseCase.getUsers().collect{ resource ->
                when (resource.status) {
                    Status.SUCCESS -> dashboardState.postValue(Success((resource.data as ArrayList<UserNetworkModel>).toMutableList().map { User(it.id,it.name,it.birthdate)}))
                    Status.LOADING -> dashboardState.postValue(Loading)
                    Status.ERROR -> dashboardState.postValue(Error(resource))
                }
            }

        }
    }
}