package com.example.opulexpropertymanagement.ab_view_models

import android.view.View
import androidx.lifecycle.*
import com.example.opulexpropertymanagement.aa_repo.NetworkClient
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.ac_ui.GlobalRepo
import com.example.opulexpropertymanagement.ac_ui.User
import com.example.tmcommonkotlin.logz
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

// this is intended to be an activity-level VM
object GlobalVM : ViewModel() {
    val repo = GlobalRepo

    val user = MediatorLiveData<User?>() // Other fragment-level VMs require this
    val userType by lazy { MutableLiveData<UserType>() }

    init {
        user.value = GlobalRepo.sharedPref.getUserFromSharedPref()
        user.addSource(repo.liveDataTryLogin) {
            user.value = it.user
            user.value?.usertype = userType.value?.let { it.toNetworkRecognizedString }?:""
        }
    }

    fun logout() {
        user.value = null
    }

    // extras

    fun switchToggled(view: View) {

    }

    fun whipeDBAndAddUser() {
        user.value?.apply {
            GlobalRepo.whipeDBAndAddUser(this)
        }
    }

    fun printSomething() {
        logz("${user.value?.email}")
    }

    fun doSomething() {
        logz("doSomething`")
        logz(NetworkClient.addProperty("","","","","","","","","","","").toString())
    }

}