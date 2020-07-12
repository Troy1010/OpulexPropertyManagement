package com.example.opulexpropertymanagement.layers.data_layer

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.layers.z_ui.GlobalRepo
import com.example.opulexpropertymanagement.App
import com.example.opulexpropertymanagement.layers.data_layer.network.apiClient
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.tmcommonkotlin.Coroutines

open class RegisterRepo {
    init {
        App.appComponent.injectRegisterRepo(this)
    }
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
                apiClient.register(email, email, password, userType.toNetworkRecognizedString).string()
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