package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.*
import com.example.opulexpropertymanagement.aa_repo.NetworkClient
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.util.toLiveData
import com.example.tmcommonkotlin.logx
import com.example.tmcommonkotlin.logz
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

class UserVM : ViewModel() {
    val disposables by lazy { CompositeDisposable() }

    val user = MediatorLiveData<User?>()
    val userType by lazy { MutableLiveData<UserType>() }
    val jobs = ArrayList<Job>()

    init {
        user.value = Repo.sharedPref.getUserFromSharedPref()
        user.addSource(Repo.streamTryLogin.toLiveData()) {
            user.value = it.user
        }
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

    fun doSomething() {
        logz("doSomething`")
        logz(NetworkClient.addProperty("","","","","","","","","","","").toString())
    }

}