package com.example.opulexpropertymanagement.ui

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.models.UserType
import com.example.tmcommonkotlin.inheritables.TMViewModel
import com.example.tmcommonkotlin.logz
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class UserVM : TMViewModel(), IRepo by Repo {
    val userStateStreamLiveData by lazy { MutableLiveData<UserState>() }
    val userType by lazy { MutableLiveData<UserType>() }

    private val setUserTypeSubject by lazy { PublishSubject.create<UserType>() }
    init {
        disposables.add(
            userStateStream
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    userStateStreamLiveData.value = it
                }
        )
    }

    fun setUserType(userType: UserType) {
        setUserTypeSubject.onNext(userType)
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