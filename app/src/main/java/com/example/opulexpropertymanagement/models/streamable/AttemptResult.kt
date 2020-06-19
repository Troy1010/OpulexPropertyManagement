package com.example.opulexpropertymanagement.models.streamable

sealed class AttemptResult {
    object Success : AttemptResult()
    class Failure(val msg:String): AttemptResult()
}