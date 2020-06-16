package com.example.pg_mvvm.repo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pg_mvvm.models.User

@Dao
interface UserDao {
    @Insert
    fun addUser(user: User)

    @Query("select * from User")
    fun getUsers(): List<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("select * from User WHERE id = :id")
    fun getUserByID(id : Int) : LiveData<User>

    @Query("DELETE FROM User")
    fun clear()
}