package com.example.opulexpropertymanagement.models.network_responses

data class ForgotPasswordResponse(
    val msg: String,
    val useremail: String,
    val userpassword: String
)