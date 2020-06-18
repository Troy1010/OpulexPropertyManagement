package com.example.opulexpropertymanagement.models.streamable

import com.example.opulexpropertymanagement.ui.User

sealed class StreamableLoginAttemptResponse {
    class Error(val msg:String) : StreamableLoginAttemptResponse()
    class Success(val user: User) : StreamableLoginAttemptResponse()
}