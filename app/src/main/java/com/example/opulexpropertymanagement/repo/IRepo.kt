package com.example.opulexpropertymanagement.ui

import io.reactivex.Observable

interface IRepo {
    fun tryLogin(user: User)
    fun logout()
    val userStateStream: Observable<UserState>
    fun whipeDBAndAddUser(user: User)
    fun getFirstUserInDB() : User?
}