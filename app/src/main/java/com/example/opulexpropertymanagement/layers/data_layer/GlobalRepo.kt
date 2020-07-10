package com.example.opulexpropertymanagement.layers.z_ui

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.opulexpropertymanagement.layers.data_layer.network.apiClient
import com.example.opulexpropertymanagement.models.network_responses.Message
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz
import com.google.gson.Gson
import java.lang.IndexOutOfBoundsException


object GlobalRepo {
    //
    fun getUserEmail(id:String): String {
        return streamLoginAttemptResult.value?.user?.email?:""
    }

    // Network
    //  TryLogin
    val streamLoginAttemptResult by lazy { MutableLiveData<TryLoginResult>() } // Observed by multiple VMs

    fun tryLogin(email: String?, password: String?) {
        if ((email==null)||(password==null)) {
            streamLoginAttemptResult.value = TryLoginResult.Failure.InvalidInput
            return
        }
        Coroutines.ioThenMain(
            {
                val responseString = apiClient.tryLogin(email, password).await().string()
                if ("success" in responseString) {
                    val user = Gson().fromJson(responseString, User::class.java)
                    TryLoginResult.Success(user)
                } else if ("Email is not register" in responseString) {
                    TryLoginResult.Failure.IncorrectEmail
                } else if ("try in next 5 mins" in responseString) {
                    TryLoginResult.Failure.TryIn5Mins
                } else if ("\"msg\":[" in responseString) {
                    val msg = try {
                        val item = Gson().fromJson(responseString, Message::class.java).msg[0]
                        if (item is Double) {
                            "Incorrect Password. Login attempts Left: ${item.toInt() + 1}"
                        } else {
                            "Login Failed"
                        }
                    } catch (e: Exception) {
                        when (e) {
                            is com.google.gson.JsonSyntaxException -> "Login Failed"
                            is IndexOutOfBoundsException -> "Login Failed"
                            else -> throw(e)
                        }
                    }
                    TryLoginResult.Failure.UnknownMsg(msg)
                } else {
                    TryLoginResult.Failure.Unknown(responseString)
                }
            },
            {
                logz("userID:${it?.user?.id}")
                streamLoginAttemptResult.value = it
            }
        )
    }
}