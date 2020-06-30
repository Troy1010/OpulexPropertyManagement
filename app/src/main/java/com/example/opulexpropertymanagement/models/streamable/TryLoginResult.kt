package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.aa.ui.User

sealed class TryLoginResult {
    abstract val user: User?
    sealed class Failure : TryLoginResult() {
        override val user: User? = null
        object TryIn5Mins: Failure()
        object IncorrectEmail: Failure()
        object InvalidInput: Failure()
        class UnknownMsg(val msg:String): Failure()
        class Unknown(val msg:String): Failure()
    }
    class Success(override val user: User?) : TryLoginResult()
}