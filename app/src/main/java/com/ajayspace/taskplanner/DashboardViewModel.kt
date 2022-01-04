package com.ajayspace.taskplanner

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajayspace.taskplanner.database.TaskDao
import com.ajayspace.taskplanner.database.TodoItem
import kotlinx.coroutines.launch

class DashboardViewModel(private val dao: TaskDao, application: Application) : ViewModel() {

    private var initialized = false;

    var tasksData = MutableLiveData<List<TodoItem>>()
    lateinit var tasksList: List<TodoItem>
    val counter = MutableLiveData<String>()

    var data = 0;

    var initAlready = MutableLiveData(false);

    var isCompletedClicked = MutableLiveData(false)
    var isProgressClicked = MutableLiveData(false)
    var isInCompletedClicked = MutableLiveData(false)


    init {
        tasksList = arrayListOf()
        initialize()
        // Log.i("DashboardViewModel", "DashboardViewModel created! ${tasksData.value}")
    }

    private fun clearData() {
        viewModelScope.launch {
            dao.clear()
        }
    }

    private fun initialize() {
        Log.i("fx", "initialize-->")
        viewModelScope.launch {
            Log.i("room", "initialize.viewScope.launch-->")
            getTasks()

        }
    }

    private suspend fun getTasks() {
        Log.i("fx", "getAllIncompleteTasks-->")
        var tasks = dao.getAllIncompleteTasks()
        if (tasks != null) {
            if (tasks.isNotEmpty()) {
                tasksList = tasks
                tasksData.value = tasksList
            }
        }
        Log.i("fx", "getAllIncompleteTasks-->$tasksList")
    }


    fun addTask(task: TodoItem) {
        Log.i("fx", "addTask-->${task.title.toString()}")
        viewModelScope.launch {
            dao.insert(task)
        }
    }


    fun updateTaskItem(task: TodoItem) {
        Log.i("fx", "updateTaskItem-->${task.title.toString()}")
        viewModelScope.launch {
            updateTask(task)
        }
    }

    private suspend fun updateTask(todo: TodoItem) {
        dao.updateTask(todo)
    }

    fun getTasksWithStatus(s: String) {
        Log.i("sharedViewmodel", "$s data -- >called")
        viewModelScope.launch {
            getItemsWithStatus(s)
        }

    }

    private suspend fun getItemsWithStatus(s: String) {
        var tasks = dao.getTasksWithStatus(s)
        Log.i("sharedViewmodel", "$s -->$tasks")
        if (tasks != null) {
            if (tasks.isNotEmpty()) {
                tasksList = tasks
                tasksData.value = tasksList
            }
        }
    }

}

