package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.*
import com.example.opulexpropertymanagement.aa_repo.NetworkClient
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.tmcommonkotlin.logz
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

// this is intended to be an activity-level VM
class UserVM : ViewModel() {
    val repo = Repo
    val disposables by lazy { CompositeDisposable() }

    companion object {
        val user = MediatorLiveData<User?>() // Other fragment-level VMs require this
    }
    val user = UserVM.user
    val userType by lazy { MutableLiveData<UserType>() }
    val jobs = ArrayList<Job>()

    init {
        user.value = Repo.sharedPref.getUserFromSharedPref()
        user.addSource(repo.liveDataTryLogin) {
            user.value = it.user
            val x = userType.value
            if (x != null) {
                user.value?.usertype = if (x == UserType.Landlord) "landlord" else "tenant"
            }
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