package com.example.opulexpropertymanagement.ui

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.models.UserType
import com.example.tmcommonkotlin.inheritables.TMViewModel
import com.example.tmcommonkotlin.logz
import io.reactivex.android.schedulers.AndroidSchedulers

class UserVM : TMViewModel(), IRepo by Repo {
    val userStateStreamLiveData by lazy { MutableLiveData<UserState>() }
    val userType by lazy { MutableLiveData<UserType>() }
    val hasLogin by lazy { MutableLiveData<Boolean>().apply { value = false } }
    init {
        disposables.add(
            userStateStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    userStateStreamLiveData.value = it
                    hasLogin.value = it.hasLogin
                }
        )
    }

    fun whipeDBAndAddUser() {
        userStateStreamLiveData.value?.user?.apply {
            whipeDBAndAddUser(this)
        }
    }

    fun printDBUser() {
        logz("${getFirstUserInDB()}")
    }
}