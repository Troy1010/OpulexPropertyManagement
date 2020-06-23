package com.example.opulexpropertymanagement.ac_ui

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.opulexpropertymanagement.aa_repo.network.NetworkClient
import com.example.opulexpropertymanagement.aa_repo.SharedPref
import com.example.opulexpropertymanagement.models.network_responses.Message
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz
import com.google.gson.Gson
import java.lang.IndexOutOfBoundsException


object GlobalRepo {
    // SharedPref
    val sharedPref = SharedPref

    // Network
    //  TryLogin
    val liveDataTryLogin by lazy { MutableLiveData<TryLoginResult>() } // Observed by multiple VMs

    fun tryLogin(email: String, password: String) {
        Coroutines.ioThenMain(
            {
                val responseString = NetworkClient.tryLogin(email, password).await().string()
                if ("success" in responseString) {
                    val user = Gson().fromJson(responseString, User::class.java)
                    logz("SuccessfulLogin`user:$user")
                    TryLoginResult.Success(user)
                } else if ("Email is not register" in responseString) {
                    TryLoginResult.Failure.IncorrectEmail
                } else if ("try in next 5 mins" in responseString) {
                    TryLoginResult.Failure.TooManyAttempts
                } else if ("\"msg\":[" in responseString) {
                    logz("responseString:$responseString")
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
                liveDataTryLogin.value = it
            }
        )
    }

    // Database
    fun whipeDBAndAddUser(user: User) {
        dao.clear()
        dao.addUser(user)
    }

    fun getFirstUserInDB(): User? {
        val users = dao.getUsers()
        if (users.isEmpty()) {
            return null
        } else {
            return users[0]
        }
    }
}