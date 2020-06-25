package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opulexpropertymanagement.aa_repo.LoginRepo
import com.example.opulexpropertymanagement.ac_ui.GlobalRepo
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.tmcommonkotlin.logv
import com.example.tmcommonkotlin.logz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginVM : ViewModel() {
    fun tryLogin() {
        if (isLockedOut.value!=true) {
            globalRepo.tryLogin(enteredEmail.value, enteredPassword.value)
        } else {
            globalRepo.streamLoginAttemptResult.value = TryLoginResult.Failure.TooManyAttempts
        }
    }

    val globalRepo = GlobalRepo
    val loginRepo = LoginRepo()

    val enteredEmail by lazy { MutableLiveData<String>() }
    val enteredPassword by lazy { MutableLiveData<String>() }
    val isLockedOut = MediatorLiveData<Boolean>()
    init {
        isLockedOut.addSource(loginRepo.streamLoginAttemptOverloadTimestamp) {
            val timeSinceAttempt = (System.currentTimeMillis()/1000) - it
            val totalTimeToWait = 10
            isLockedOut.value = timeSinceAttempt < totalTimeToWait
            if (isLockedOut.value==true) {
                viewModelScope.launch {
                    delay((totalTimeToWait-timeSinceAttempt+1)*1000)
                    withContext(viewModelScope.coroutineContext + Dispatchers.Main) {
                        loginRepo.streamLoginAttemptOverloadTimestamp.value = loginRepo.streamLoginAttemptOverloadTimestamp.value
                    }
                }
            }
        }
    }
}