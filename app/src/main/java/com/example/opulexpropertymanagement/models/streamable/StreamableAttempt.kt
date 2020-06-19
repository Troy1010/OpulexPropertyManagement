package com.example.opulexpropertymanagement.models.streamable

sealed class StreamableAttempt {
    object Success : StreamableAttempt()
    class Failure(val msg:String): StreamableAttempt()
}