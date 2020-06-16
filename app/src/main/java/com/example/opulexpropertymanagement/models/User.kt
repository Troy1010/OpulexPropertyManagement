package com.example.opulexpropertymanagement.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    val email:String,
    val password:String,
    val mobile:String = "1112223333", // TODO
    val firstName:String = "Bob", // TODO
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)