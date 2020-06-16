package com.example.opulexpropertymanagement.models.network_responses

import com.example.opulexpropertymanagement.ui.User


data class LoginResponse (
    val token: String,
    val user: User
)