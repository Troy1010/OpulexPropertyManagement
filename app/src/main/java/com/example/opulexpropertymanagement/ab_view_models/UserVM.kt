package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.*
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

class UserVM : ViewModel() {
    val disposables by lazy { CompositeDisposable() }

    val user = MediatorLiveData<User?>()
    val userType by lazy { MutableLiveData<UserType>() }
    val jobs = ArrayList<Job>()

    init {
//        user.addSource(LiveDataReactiveStreams.fromPublisher(loginAttemptResponse)) {
//            if (it is StreamableLoginAttemptResponse.Success) {
//                user.value = it.user
//            } else {
//                user.value = null
//            }
//        }
//        user.value = SharedPref.getUserFromSharedPref()
    }

    fun logout() {
        user.value = null
    }

    fun whipeDBAndAddUser() {
        user.value?.apply {
            Repo.whipeDBAndAddUser(this)
        }
    }

    fun printSomething() {
        logz("${user.value?.email}")
    }

    fun finalize() {
        for (job in jobs) {
            job.cancel()
        }
        disposables.dispose()
    }
}