package com.example.passwordgenerator

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.passwordgenerator.database.PasswordDataClass
import com.example.passwordgenerator.database.PasswordDatabaseDao
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class SharedViewModel(
    private val dataSource: PasswordDatabaseDao,
    val view: View
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var password = ""
    var website = ""
      
    private val latestPassword = dataSource.previousPassword()
    val allPasswords = dataSource.getAllPasswords()

    fun insertPassword(){
        uiScope.launch {
            val newPassword = PasswordDataClass(password = password, website = website)
            if (latestPassword.value?.website != newPassword.website) {
                suspendInsertPassword(newPassword)
            }
        }
    }

    private suspend fun suspendInsertPassword(newPassword: PasswordDataClass){
        withContext(Dispatchers.IO){
            dataSource.insertName(newPassword)
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
            Snackbar.make(view, "All your data is gone forever", Snackbar.LENGTH_SHORT).show()
        }
    }

    private suspend fun suspendClearAll(){
        withContext(Dispatchers.IO){
            dataSource.clear()
        }
    }
}