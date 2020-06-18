package com.example.opulexpropertymanagement.ac_ui

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    val appapikey: String,
    val msg: String,
    @SerializedName("useremail")
    val email: String,
    @PrimaryKey
    @SerializedName("userid")
    val id: String,
    val usertype: String
)