package com.example.opulexpropertymanagement.models.streamable

sealed class RegisterResult {
    object Success : RegisterResult()
    sealed class Failure: RegisterResult() {
        abstract val msg:String
        object Unknown: Failure() {
            override val msg = "Unknown failure"
        }
        object EmailAlreadyExists: Failure() {
            override val msg = "Email already exists"
        }
    }
}