package com.example.opulexpropertymanagement.ui

import io.reactivex.Observable

interface IRepo {
    fun tryLogin(user: User)
    fun register(email:String, password:String)
    fun tryLogin(email:String, password:String)
    fun logout()
//    val userStateStream: Observable<UserState>
    fun whipeDBAndAddUser(user: User)
    fun getFirstUserInDB() : User?
}