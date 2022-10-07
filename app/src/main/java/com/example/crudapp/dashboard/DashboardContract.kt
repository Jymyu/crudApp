package com.example.crudapp.dashboard

import androidx.lifecycle.LiveData
import com.example.crudapp.model.User
import com.example.crudapp.network.Resource

sealed class DashboardContract {

    interface ViewModel {
        val dashboardState: LiveData<State>
        val dashboardEvent: LiveData<Event>
        fun invokeAction(action: Action)
    }

    sealed class Action {
        object FetchUsers : Action()
        data class PutUser(val user: User?) : Action()
    }

    sealed class State {
        object Loading : State()
        data class Success(val users: List<User>?) : State()
        data class Error(val res: Resource<*>) : State()
        data class SuccessMessage(val successMessage: String?) : State()
    }

    sealed class Event {
        object DummyEvent : Event()
    }
}
