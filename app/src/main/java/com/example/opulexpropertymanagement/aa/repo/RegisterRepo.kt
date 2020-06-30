package com.example.opulexpropertymanagement.aa.repo

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.aa.repo.network.INetworkClient
import com.example.opulexpropertymanagement.aa.repo.network.NetworkClient
import com.example.opulexpropertymanagement.aa.ui.GlobalRepo
import com.example.opulexpropertymanagement.app.App
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.tmcommonkotlin.Coroutines
import javax.inject.Inject

open class RegisterRepo {
    @Inject
    lateinit var networkClient2: INetworkClient
    init {
        App.component.injectRegisterRepo(this)
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
                networkClient2.register(email, email, password, userType.toNetworkRecognizedString)
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