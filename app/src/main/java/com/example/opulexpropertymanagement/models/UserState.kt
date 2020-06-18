package com.example.opulexpropertymanagement.ac_ui

data class UserState(
    val wantsLogin:Boolean = false,
    val hasLogin:Boolean = false,
    val user: User?=null,
    val errorReason:String?=null
)