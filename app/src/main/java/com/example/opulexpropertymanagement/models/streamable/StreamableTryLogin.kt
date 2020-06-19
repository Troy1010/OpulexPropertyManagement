package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.ac_ui.User

sealed class StreamableTryLogin {
    abstract val user: User?
    class Failure(val msg:String) : StreamableTryLogin() {
        override val user: User? = null
    }
    class Success(override val user: User?) : StreamableTryLogin()
}