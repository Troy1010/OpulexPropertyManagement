package com.example.opulexpropertymanagement.ui.ViewModels

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ui.IRepo
import com.example.opulexpropertymanagement.ui.Repo
import com.example.opulexpropertymanagement.ui.UserState
import com.example.tmcommonkotlin.inheritables.TMViewModel
import com.example.tmcommonkotlin.logz
import io.reactivex.android.schedulers.AndroidSchedulers

class UserVM : TMViewModel(), IRepo by Repo {
    val userStateStreamLiveData by lazy { MutableLiveData<UserState>() }
    val userType by lazy { MutableLiveData<UserType>() }
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