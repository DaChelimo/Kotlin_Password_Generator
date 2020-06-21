package com.example.passwordgenerator.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwordDataClass")
data class PasswordDataClass(
    @PrimaryKey(autoGenerate = true)
    val password_id: Long = 0L,

    @ColumnInfo
    val website: String,

    @ColumnInfo
    val password: String
)