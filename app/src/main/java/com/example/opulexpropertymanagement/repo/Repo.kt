package com.example.opulexpropertymanagement.ui

import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttempt
import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttemptResponse
import com.example.opulexpropertymanagement.repo.network.NetworkClient
import com.example.tmcommonkotlin.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject


object Repo : IRepo {
    override fun whipeDBAndAddUser(user: User) {
        dao.clear()
        dao.addUser(user)
    }

    override fun getFirstUserInDB(): User? {
        val users = dao.getUsers()
        if (users.isEmpty()) {
            return null
        } else {
            return users[0]
        }
    }

    private val tryToLoginSubject by lazy { PublishSubject.create<StreamableLoginAttempt>() }
    override lateinit var loginAttemptResponse: Flowable<StreamableLoginAttemptResponse>

    init {
        loginAttemptResponse = tryToLoginSubject
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap {
                NetworkClient.login(it.email, it.password)
                    .map<StreamableLoginAttemptResponse> { user ->
                        StreamableLoginAttemptResponse.Success(user)
                    }
                    .onErrorReturn {
                        StreamableLoginAttemptResponse.Error("$it")
                    }
            }
            .toFlowable(BackpressureStrategy.DROP)
    }

    override fun register(email: String, password: String) {
        NetworkClient.register(email, email, password, UserType.Tenant.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .logSubscribe()
    }


    override fun tryLogin(email: String, password: String) {
        tryToLoginSubject.onNext(
            StreamableLoginAttempt(email, password)
        )
    }
}