package com.example.pg_mvvm.repo

import com.example.pg_mvvm.models.User
import com.example.pg_mvvm.models.UserState
import io.reactivex.Observable

interface IRepo {
    fun tryLogin(user: User)
    fun logout()
    val userStateStream: Observable<UserState>
    fun whipeDBAndAddUser(user: User)
    fun getFirstUserInDB() : User?
}