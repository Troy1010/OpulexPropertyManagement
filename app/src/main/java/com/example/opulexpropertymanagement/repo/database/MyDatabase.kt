package com.example.opulexpropertymanagement.ui

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.opulexpropertymanagement.app.App


val db = Room
    .databaseBuilder(App, MyDatabase::class.java, "mydb")
    .allowMainThreadQueries() // TODO remove
    .build()
val dao = db.getUserDao()

@Database(entities = [User::class], version = 2)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}

