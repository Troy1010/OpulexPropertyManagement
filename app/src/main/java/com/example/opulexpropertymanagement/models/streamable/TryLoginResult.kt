package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.ac_ui.User

sealed class TryLoginResult {
    abstract val user: User?
    class Failure(val msg:String) : TryLoginResult() {
        override val user: User? = null
    }
    class Success(override val user: User?) : TryLoginResult()
}