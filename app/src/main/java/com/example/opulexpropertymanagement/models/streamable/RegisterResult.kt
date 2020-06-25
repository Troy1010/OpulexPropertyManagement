package com.example.opulexpropertymanagement.models.streamable

sealed class RegisterResult {
    object Success : RegisterResult()
    sealed class Failure: RegisterResult() {
        abstract val msg:String
        object Unknown: Failure() {
            override val msg = "Unknown failure"
        }
        object EmailWasNull: Failure() {
            override val msg = "Email required"
        }
        object PasswordWasNull: Failure() {
            override val msg = "Password required"
        }
        object UserTypeWasNull: Failure() {
            override val msg = "User type required"
        }
        object EmailAlreadyExists: Failure() {
            override val msg = "Email already exists"
        }
    }
}