package com.example.passwordgenerator

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.database.PasswordDatabaseDao

class SharedViewModelFactory(
    private val dataSource: PasswordDatabaseDao,
    private val view: View
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(
                dataSource,
                view
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
