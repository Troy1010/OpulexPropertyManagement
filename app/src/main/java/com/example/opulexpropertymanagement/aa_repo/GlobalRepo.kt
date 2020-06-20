package com.example.opulexpropertymanagement.ac_ui

import androidx.lifecycle.MutableLiveData
import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.TryLoginResult
import com.example.opulexpropertymanagement.aa_repo.NetworkClient
import com.example.opulexpropertymanagement.aa_repo.SharedPref
import com.example.opulexpropertymanagement.models.Property
import com.example.opulexpropertymanagement.models.streamable.AddPropertyResult
import com.example.opulexpropertymanagement.models.streamable.GetPropertiesResult
import com.example.opulexpropertymanagement.models.streamable.RegisterResult
import com.example.tmcommonkotlin.Coroutines
import com.example.tmcommonkotlin.logz
import com.google.gson.Gson


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
                } else {
                    TryLoginResult.Failure("Unknown error")
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