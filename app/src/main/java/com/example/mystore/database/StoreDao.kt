package com.example.mystore.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mystore.database.models.UserR

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(activationModelR: UserR?)

    @Query("SELECT * FROM UserR")
    fun getUsers(): List<UserR?>?

    @Query("DELETE FROM UserR")
    fun clearUserR()

    @Query("DELETE FROM UserR WHERE login =:login")
    fun deleteUser(login : String)
}