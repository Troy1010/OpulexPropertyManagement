package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.ui.User

sealed class StreamableLoginAttempt {
    class Failure(val msg:String) : StreamableLoginAttempt()
    class Success(val user: User) : StreamableLoginAttempt()
}