package com.example.opulexpropertymanagement.ui

import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttempt
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttemptResponse
import com.example.opulexpropertymanagement.repo.NetworkClient
import com.example.opulexpropertymanagement.repo.SharedPref
import com.example.tmcommonkotlin.*
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

    suspend fun register(email: String, password: String)= withContext(Dispatchers.IO) {
        val result = NetworkClient.register(email, email, password, UserType.Tenant.name)
            .await()
        result
    }

    suspend fun tryLogin(email: String, password: String): User {
        return NetworkClient.tryLogin(email, password)
            .await()
    }


//    override fun tryLogin(email: String, password: String) {
//        tryToLoginSubject.onNext(
//            StreamableLoginAttempt(email, password)
//        )
//    }
}