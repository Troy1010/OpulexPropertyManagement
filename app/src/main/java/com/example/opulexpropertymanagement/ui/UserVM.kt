package com.example.opulexpropertymanagement.ui

import androidx.lifecycle.MutableLiveData
import com.example.tmcommonkotlin.inheritables.TMViewModel
import com.example.tmcommonkotlin.logz
import io.reactivex.android.schedulers.AndroidSchedulers

class UserVM : TMViewModel(), IRepo by Repo {
    val userStateStreamLiveData by lazy { MutableLiveData<UserState>() }
    init {
        disposables.add(
            userStateStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    userStateStreamLiveData.value = it
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