package com.example.opulexpropertymanagement.ac_ui

import androidx.lifecycle.LiveData
import androidx.room.*

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