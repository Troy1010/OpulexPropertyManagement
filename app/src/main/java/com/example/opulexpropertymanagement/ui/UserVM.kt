package com.example.opulexpropertymanagement.ui

import androidx.lifecycle.*
import com.example.opulexpropertymanagement.app.App
import com.example.opulexpropertymanagement.app.Config
import com.example.opulexpropertymanagement.repo.SharedPref
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttemptResponse
import com.example.tmcommonkotlin.logSubscribe
import com.example.tmcommonkotlin.logz
import io.reactivex.disposables.CompositeDisposable

class UserVM : ViewModel(), IRepo by Repo {
    val disposables by lazy { CompositeDisposable() }

    val user = MediatorLiveData<User?>()
    val userType by lazy { MutableLiveData<UserType>() }

    init {
        logz("UserVM`init")
        user.addSource(LiveDataReactiveStreams.fromPublisher(loginAttemptResponse)) {
            if (it is StreamableLoginAttemptResponse.Success) {
                logz("user.addSource`loginAttemptResponse`Success")
                user.value = it.user
            } else {
                logz("user.addSource`loginAttemptResponse`Failure")
                user.value = null
            }
        }
        loginAttemptResponse
            .logSubscribe("zzzzzzzzz")
        user.value = SharedPref.getUserFromSharedPref()
    }

    fun whipeDBAndAddUser() {
        user.value?.apply {
            whipeDBAndAddUser(this)
        }
    }

    fun printDBUser() {
        logz("${getFirstUserInDB()}")
    }

    fun finalize() {
        disposables.dispose()
    }
}