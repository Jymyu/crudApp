package com.example.crudapp.addUser

import androidx.lifecycle.LiveData
import com.example.crudapp.model.User
import com.example.crudapp.network.Resource

sealed class AddUserContract {

    interface ViewModel {
        val addUserState: LiveData<State>
        fun invokeAction(action: Action)
    }

    sealed class Action {
        data class PutUser(val name: String, val birthDate: String) : Action()
    }

    sealed class State {
        object Loading : State()
        data class Error(val errorMessage: String) : State()
        data class SuccessMessage(val successMessage: String?) : State()
    }
}
