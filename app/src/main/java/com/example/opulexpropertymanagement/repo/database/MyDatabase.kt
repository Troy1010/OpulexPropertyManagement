package com.example.pg_mvvm.repo.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pg_mvvm.app.App
import com.example.pg_mvvm.models.User


val db = Room
    .databaseBuilder(App, MyDatabase::class.java, "mydb")
    .allowMainThreadQueries()
    .build()
val dao = db.getUserDao()

@Database(entities = [User::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}

