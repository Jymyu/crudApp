package com.example.crudapp.addUser

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudapp.addUser.AddUserContract.State.*
import com.example.crudapp.model.User
import com.example.crudapp.model.asNetworkModel
import com.example.crudapp.network.Status
import com.example.crudapp.network.UserNetworkModel
import com.example.crudapp.useCases.UsersUseCase
import kotlinx.coroutines.launch

class AddUserViewModel(
    private val usersUseCase: UsersUseCase,
) : ViewModel(), AddUserContract.ViewModel {


    override val addUserState = MediatorLiveData<AddUserContract.State>()

    override fun invokeAction(action: AddUserContract.Action) {
        when(action){
            is AddUserContract.Action.PutUser -> putUser(action.name,action.birthDate)
        }
    }

    private fun putUser(name: String, birthDate: String) {

        if (name.isEmpty()){
            addUserState.postValue(Error("Name is empty"))
            return
        }
        if (birthDate.isEmpty()){
            addUserState.postValue(Error("Birthdate is empty"))
            return
        }

        val user = User(name = name, birthDate = birthDate)

            viewModelScope.launch {
                usersUseCase.putUser(
                    user.asNetworkModel()
                ).collect{resource ->
                    when (resource.status) {
                        Status.SUCCESS -> addUserState.postValue(
                            SuccessMessage(
                               "User inserted successfully"
                            )
                        )
                        Status.LOADING -> addUserState.postValue(Loading)
                        Status.ERROR -> addUserState.postValue(Error(resource.message ?: "API ERROR.. Sorry"))
                    }

                }
            }
    }
}