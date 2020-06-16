package com.example.opulexpropertymanagement.models.network_responses

import com.example.pg_mvvm.models.User

data class LoginResponse (
    val token: String,
    val user: User
)