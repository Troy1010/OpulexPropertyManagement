package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.ac_ui.User

sealed class LoginAttempt {
    class Failure(val msg:String) : LoginAttempt()
    class Success(val user: User) : LoginAttempt()
}