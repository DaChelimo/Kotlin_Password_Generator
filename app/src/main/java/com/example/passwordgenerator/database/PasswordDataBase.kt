package com.example.passwordgenerator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PasswordDataClass::class], version = 1, exportSchema = false)
abstract class PasswordDataBase: RoomDatabase(){

    abstract val passwordDatabaseDao: PasswordDatabaseDao

    companion object{
        @Volatile
        var INSTANCE: PasswordDataBase? = null

        fun getInstance(context: Context): PasswordDataBase? {
            var instance = INSTANCE
            if (instance == null){
                instance = Room.databaseBuilder(context.applicationContext, PasswordDataBase::class.java, "namesDatabase")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}