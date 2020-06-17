package com.example.opulexpropertymanagement.ui

import com.example.opulexpropertymanagement.models.UserType
import com.example.opulexpropertymanagement.repo.network.NetworkClient
import com.example.tmcommonkotlin.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

//AndroidSchedulers.mainThread()

object Repo : IRepo {
//    override lateinit var userStateStream: Observable<UserState>
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

    private val tryToLoginSubject by lazy { PublishSubject.create<User>() }
    private val logoutSubject by lazy { PublishSubject.create<Unit>() }

    init {
//        userStateStream = tryToLoginSubject
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .flatMap { user ->
//                NetworkClient.login(user.email, user.pa)
//                    .map {
//                        UserState(true, true, it.user)
//                    }
//                    .onErrorReturn {
//                        UserState(true, false, user, it.message)
//                    }
//            }
//            .mergeWith(logoutSubject
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .map { UserState() })
//            .startWith(UserState())
//            .replay(1).refCount()
//        userStateStream.subscribe()
    }

    override fun register(email: String, password: String) {
//        logx(NetworkClient.register(email, email, password, UserType.Tenant.name)
//            .toString())
        NetworkClient.register(email, email, password, UserType.Tenant.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .logSubscribe()
    }

    override fun tryLogin(user: User) {
        tryToLoginSubject.onNext(user)
    }

    override fun tryLogin(email: String, password: String) {
        logx()
        logz("NetworkClient.login(email, password):${NetworkClient.login(email, password)}")

    }

    override fun logout() {
        logx()
        logoutSubject.onNext(Unit)
    }
}