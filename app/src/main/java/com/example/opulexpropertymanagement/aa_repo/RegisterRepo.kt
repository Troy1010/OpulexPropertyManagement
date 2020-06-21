package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.opulexpropertymanagement.ac_ui.GlobalRepo
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.tmcommonkotlin.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterRepo {
    val streamTryRegisterResult by lazy { MutableLiveData<RegisterResult>() }

    fun tryRegister(email: String, password: String, userType: UserType) {
        Coroutines.ioThenMain({
            val resultString =
                NetworkClient.register(email, email, password, userType.toNetworkRecognizedString)
                    .await().string()
            if ("success" in resultString) {
                GlobalRepo.tryLogin(email, password)
                RegisterResult.Success
            } else if ("Email already exsist" in resultString) {
                RegisterResult.Failure.EmailAlreadyExists
            } else {
                RegisterResult.Failure.Unknown
            }
        }, {
            streamTryRegisterResult.value = it
        })
    }
}