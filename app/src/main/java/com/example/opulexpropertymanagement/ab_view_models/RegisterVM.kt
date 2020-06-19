package com.example.opulexpropertymanagement.ab_view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opulexpropertymanagement.ac_ui.Repo
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.tmcommonkotlin.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterVM: ViewModel() {
    val registrationAttempt by lazy { MutableLiveData<RegisterResult>() }
    fun register(email: String, password: String, userType: UserType) {
        viewModelScope.launch {
            val x = Repo.register(email, password, userType)
            withContext(Dispatchers.Main) {
                registrationAttempt.value = x
            }
        }
    }
}