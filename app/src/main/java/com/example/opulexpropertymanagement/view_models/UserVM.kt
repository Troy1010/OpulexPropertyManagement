package com.example.opulexpropertymanagement.view_models

import androidx.lifecycle.*
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ui.Repo
import com.example.opulexpropertymanagement.ui.User
import com.example.tmcommonkotlin.logz
import io.reactivex.disposables.CompositeDisposable

class UserVM : ViewModel() {
    val disposables by lazy { CompositeDisposable() }

    val user = MediatorLiveData<User?>()
    val userType by lazy { MutableLiveData<UserType>() }

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

    fun printDBUser() {
        logz("${Repo.getFirstUserInDB()}")
    }

    fun finalize() {
        disposables.dispose()
    }
}