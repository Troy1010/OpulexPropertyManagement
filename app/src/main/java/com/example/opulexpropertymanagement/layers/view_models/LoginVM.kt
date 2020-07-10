package com.example.opulexpropertymanagement.layers.view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opulexpropertymanagement.layers.z_ui.GlobalRepo
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginVM : ViewModel() {
    fun tryLogin() {
        if (isLockedOut.value!=true) {
            globalRepo.tryLogin(enteredEmail.value, enteredPassword.value)
        } else {
            globalRepo.streamLoginAttemptResult.value = TryLoginResult.Failure.TryIn5Mins
        }
    }

    val globalRepo = GlobalRepo

    val enteredEmail by lazy { MutableLiveData<String>() }
    val enteredPassword by lazy { MutableLiveData<String>() }
    val isLockedOut = MediatorLiveData<Boolean>()
    init {
        isLockedOut.addSource(GlobalRepo.streamLoginAttemptResult) {
            if (it is TryLoginResult.Failure.TryIn5Mins) {
                isLockedOut.value = true
                viewModelScope.launch {
                    delay(300*1000)
                    withContext(Dispatchers.Main) {
                        isLockedOut.value = false
                    }
                }
            }
        }
    }
}