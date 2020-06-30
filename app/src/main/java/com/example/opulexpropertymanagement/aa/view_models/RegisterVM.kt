package com.example.opulexpropertymanagement.aa.view_models

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opulexpropertymanagement.aa.repo.RegisterRepo
import com.example.opulexpropertymanagement.models.UserType

class RegisterVM : ViewModel() {
    val repo = RegisterRepo()
    val isChecked by lazy { MutableLiveData<Boolean>().apply {
        GlobalVM.user.value?.usertype?.let {
            value = it==UserType.Landlord.toNetworkRecognizedString
        }
    } }
    val enteredEmail by lazy { MutableLiveData<String>() }
    val enteredPassword by lazy { MutableLiveData<String>() }
    val userType by lazy { MediatorLiveData<UserType>() }

    fun tryRegister() {
        repo.tryRegister(
            email = enteredEmail.value,
            password = enteredPassword.value,
            userType = userType.value
        )
    }

    init {
        userType.addSource(isChecked) {
            userType.value = if (it) UserType.Landlord else {
                UserType.Tenant
            }
        }
    }
}