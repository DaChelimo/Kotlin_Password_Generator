package com.example.passwordgenerator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.passwordgenerator.database.PasswordDataClass
import com.example.passwordgenerator.database.PasswordDatabaseDao
import com.example.passwordgenerator.formatNights
import kotlinx.coroutines.*

class SharedViewModel(private val dataSource: PasswordDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var newPassword = ""
    var website = ""
      
    private val latestPassword = dataSource.previousPassword()
    val allPasswords = dataSource.getAllPasswords()

    fun insertPassword(){
        uiScope.launch {
            val newPassword = PasswordDataClass(password = newPassword, website = website)
            if (latestPassword.value?.website != newPassword.website) {
                suspendInsertPassword(newPassword)
            }
        }
    }

    private suspend fun suspendInsertPassword(newPassword: PasswordDataClass){
        withContext(Dispatchers.IO){
            dataSource.insertName(newPassword)
            this@SharedViewModel.newPassword = ""
        }
    }

    fun removePrevious(){
        uiScope.launch {
            suspendRemovePrevious()
        }
    }

    private suspend fun suspendRemovePrevious(){
        withContext(Dispatchers.IO){
            dataSource.removeLastPassword()
        }
    }

    fun clearAll(){
        uiScope.launch {
            suspendClearAll()
        }
    }

    private suspend fun suspendClearAll(){
        withContext(Dispatchers.IO){
            dataSource.clear()
        }
    }
}