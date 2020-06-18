package com.example.opulexpropertymanagement.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttemptResponse
import com.example.tmcommonkotlin.inheritables.TMViewModel
import com.example.tmcommonkotlin.logz
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.reactivestreams.Publisher

class UserVM : ViewModel(), IRepo by Repo {
    val disposables by lazy { CompositeDisposable() }
    val user: LiveData<User?> = LiveDataReactiveStreams.fromPublisher(
        loginAttemptResponse
            .map {
                if (it is StreamableLoginAttemptResponse.Success) {
                    it.user
                } else {
                    null
                }
            }.toFlowable(BackpressureStrategy.DROP)
    )
    val userType by lazy { MutableLiveData<UserType>() }


    val userStateStreamLiveData by lazy { MutableLiveData<UserState>() }
    //    val user by lazy { MutableLiveData<User?>().apply { value = null } }

    init {

//        disposables.add(
//
//            . observeOn (AndroidSchedulers.mainThread())
//            .subscribe {
//                if (it is StreamableLoginAttemptResponse.Success) {
//                    logz("updating user value to:${it.user}")
//                    user.value = it.user
//                } else {
//                    user.value = null
//                }
//            }
//        )
//        disposables.add(
//            userStateStream
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    userStateStreamLiveData.value = it
//                    hasLogin.value = it.hasLogin
//                }
//        )
    }

    fun whipeDBAndAddUser() {
        userStateStreamLiveData.value?.user?.apply {
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