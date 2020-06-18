package com.example.opulexpropertymanagement.ui

import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttempt
import com.example.opulexpropertymanagement.repo.NetworkClient
import com.example.opulexpropertymanagement.repo.SharedPref
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


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

    suspend fun register(email: String, password: String): Boolean {
        val result = NetworkClient.register(email, email, password, UserType.Tenant.name)
            .await()
        return "success" in result.string()
    }

    suspend fun tryLogin(email: String, password: String): StreamableLoginAttempt {
        val responseString = NetworkClient.tryLogin(email, password)
            .await().string()
        if ("success" in responseString) {
            val user = Gson().fromJson(responseString, User::class.java)
            return StreamableLoginAttempt.Success(user)
        } else {
            return StreamableLoginAttempt.Failure("Unknown error")
        }
    }


//    override fun tryLogin(email: String, password: String) {
//        tryToLoginSubject.onNext(
//            StreamableLoginAttempt(email, password)
//        )
//    }
}