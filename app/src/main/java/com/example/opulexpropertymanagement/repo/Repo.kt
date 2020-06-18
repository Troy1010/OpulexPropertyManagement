package com.example.opulexpropertymanagement.ui

import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttempt
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttemptResponse
import com.example.opulexpropertymanagement.repo.NetworkClient
import com.example.tmcommonkotlin.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object Repo {
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

    private val tryToLoginSubject by lazy { PublishSubject.create<StreamableLoginAttempt>() }
    lateinit var loginAttemptResponse: Flowable<StreamableLoginAttemptResponse>

    init {
//        loginAttemptResponse = tryToLoginSubject
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .flatMap {
//                NetworkClient.login(it.email, it.password)
//                    .map<StreamableLoginAttemptResponse> { user ->
//                        StreamableLoginAttemptResponse.Success(user)
//                    }
//                    .onErrorReturn {
//                        StreamableLoginAttemptResponse.Error("$it")
//                    }
//            }
//            .toFlowable(BackpressureStrategy.DROP)

    }

    suspend fun register(email: String, password: String)= withContext(Dispatchers.IO) {
//        val result = NetworkClient.register(email, email, password, UserType.Tenant.name)
//            .await()
//        result
    }

    fun register2(email: String, password: String) {
        val x = NetworkClient.register(email, email, password, UserType.Tenant.name)
        x.enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                logz("ONFAILURE")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                logz("ONRESPONSE. response:$response")
            }
        })
    }

    fun tryLogin(email: String, password: String) {
        TODO("Not yet implemented")
    }


//    override fun tryLogin(email: String, password: String) {
//        tryToLoginSubject.onNext(
//            StreamableLoginAttempt(email, password)
//        )
//    }
}