package com.example.opulexpropertymanagement.ac_ui

import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.LoginAttempt
import com.example.opulexpropertymanagement.aa_repo.NetworkClient
import com.example.opulexpropertymanagement.aa_repo.SharedPref
import com.google.gson.Gson


object Repo {
    val sharedPref = SharedPref

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

    suspend fun register(email: String, password: String, userType: UserType): LoginAttempt {
        val result = NetworkClient.register(email, email, password, userType.name)
            .await()
        if ("success" in result.string()) {
            return tryLogin(email, password)
        } else {
            return LoginAttempt.Failure("Registration failed")
        }
    }

    suspend fun tryLogin(email: String, password: String): LoginAttempt {
        val responseString = NetworkClient.tryLogin(email, password)
            .await().string()
        if ("success" in responseString) {
            val user = Gson().fromJson(responseString, User::class.java)
            return LoginAttempt.Success(user)
        } else {
            return LoginAttempt.Failure("Unknown error")
        }
    }


//    override fun tryLogin(email: String, password: String) {
//        tryToLoginSubject.onNext(
//            StreamableLoginAttempt(email, password)
//        )
//    }
}