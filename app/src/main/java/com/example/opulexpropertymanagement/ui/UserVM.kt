package com.example.opulexpropertymanagement.ui

import androidx.lifecycle.*
import com.example.opulexpropertymanagement.app.App
import com.example.opulexpropertymanagement.app.Config
import com.example.opulexpropertymanagement.repo.SharedPref
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttemptResponse
import com.example.tmcommonkotlin.logSubscribe
import com.example.tmcommonkotlin.logx
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