package com.example.opulexpropertymanagement.models.streamable

sealed class BasicResult {
    object Success : BasicResult()
    class Failure(val msg:String): BasicResult()
}