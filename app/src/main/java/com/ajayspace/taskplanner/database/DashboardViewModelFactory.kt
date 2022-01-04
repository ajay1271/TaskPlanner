package com.ajayspace.taskplanner.database

import android.app.Application
import androidx.lifecycle.ViewModel
import com.ajayspace.taskplanner.DashboardViewModel
import androidx.lifecycle.ViewModelProvider

class DashboardViewModelFactory (
    private val dataSource: TaskDao,
    private val application: Application) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
