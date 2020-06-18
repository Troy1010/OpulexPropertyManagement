package com.example.opulexpropertymanagement.ui

import com.example.opulexpropertymanagement.models.streamable.StreamableLoginAttemptResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface IRepo {
    fun register(email:String, password:String)
    fun tryLogin(email:String, password:String)
    val loginAttemptResponse: Flowable<StreamableLoginAttemptResponse>
//    val userStateStream: Observable<UserState>
    fun whipeDBAndAddUser(user: User)
    fun getFirstUserInDB() : User?
}