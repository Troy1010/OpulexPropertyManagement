package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa_repo.LoginRepo
import com.example.opulexpropertymanagement.ac_ui.GlobalRepo
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult

class LoginVM : ViewModel() {
    fun tryLogin() {
        globalRepo.tryLogin(enteredEmail.value, enteredPassword.value)
    }

    val globalRepo = GlobalRepo
    val loginRepo = LoginRepo()

    val enteredEmail by lazy { MutableLiveData<String>() }
    val enteredPassword by lazy { MutableLiveData<String>() }
    val isLockedOut = MediatorLiveData<Boolean>()
    init {
        isLockedOut.addSource(loginRepo.streamLoginAttemptOverloadTimestamp) {
            isLockedOut.value = System.currentTimeMillis() - it > 300
        }
    }
}