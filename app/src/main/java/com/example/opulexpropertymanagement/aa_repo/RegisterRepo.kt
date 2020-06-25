package com.example.opulexpropertymanagement.aa_repo

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.aa_repo.network.NetworkClient
import com.example.opulexpropertymanagement.ac_ui.GlobalRepo
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.tmcommonkotlin.Coroutines

class RegisterRepo {
    val streamTryRegisterResult by lazy { MutableLiveData<RegisterResult>() }

    fun tryRegister(email: String?, password: String?, userType: UserType?) {
        if (email == null) {
            streamTryRegisterResult.value = RegisterResult.Failure.EmailWasNull
            return
        } else if (password==null) {
            streamTryRegisterResult.value = RegisterResult.Failure.PasswordWasNull
            return
        } else if (userType==null) {
            streamTryRegisterResult.value = RegisterResult.Failure.UserTypeWasNull
            return
        }
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