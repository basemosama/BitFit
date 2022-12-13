package com.codepath.bitfit.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepath.bitfit.database.SleepDatabase
import com.codepath.bitfit.model.SleepItem
import kotlinx.coroutines.launch


class MainViewModel(application: Application) :AndroidViewModel(application) {

    private val database = SleepDatabase.getDatabase(application.applicationContext)

    private val sleepDao = database.sleepDao()

    val sleepData = sleepDao.getSleepData()


     fun insertSleepItem(item: SleepItem?) {
         viewModelScope.launch {
             item?.let {
                 sleepDao.insertSleepItem(item)
             }
         }
    }

}