package com.example.opulexpropertymanagement.models.network_responses

data class LoginResponse(
    val appapikey: String,
    val msg: String,
    val useremail: String,
    val userid: String,
    val usertype: String
)