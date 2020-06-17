package com.example.opulexpropertymanagement.ui

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    val appapikey: String,
    val msg: String,
    @SerializedName("useremail")
    val email: String,
    @SerializedName("userid")
    val id: String,
    val usertype: String
)