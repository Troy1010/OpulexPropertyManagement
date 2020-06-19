package com.example.opulexpropertymanagement.models.streamable

sealed class StreamableRegister {
    object Success : StreamableRegister()
    sealed class Failure: StreamableRegister() {
        abstract val msg:String
        object Unknown: Failure() {
            override val msg = "Unknown failure"
        }
        object EmailAlreadyExists: Failure() {
            override val msg = "Email already exists"
        }
    }
}