package com.example.passwordgenerator.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PasswordDatabaseDao {
    @Insert
    fun insertName(password: PasswordDataClass)

    @Query("SELECT * FROM passwordDataClass ORDER BY password_id DESC")
    fun getAllPasswords(): LiveData<List<PasswordDataClass>>

    @Query("SELECT * FROM passwordDataClass ORDER BY password_id DESC LIMIT 1")
    fun previousPassword(): LiveData<PasswordDataClass>

    @Query("DELETE FROM passwordDataClass WHERE password_id = (SELECT password_id FROM passwordDataClass ORDER BY password_id DESC LIMIT 1)")
    fun removeLastPassword()

    @Query("DELETE FROM passwordDataClass")
    fun clear()
}