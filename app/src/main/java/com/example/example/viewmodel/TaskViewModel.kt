package com.example.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.example.data.AppDatabase
import com.example.example.data.Task
import com.example.example.data.TaskDao
import com.example.example.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>
    private val taskDao: TaskDao = AppDatabase.getDatabase(application).taskDao()

    init {
        val taskDao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        taskDao.updateTask(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    fun getTaskById(taskId: Int, onResult: (Task?) -> Unit) {
        viewModelScope.launch {
            val task = withContext(Dispatchers.IO){
                taskDao.getTaskById(taskId)
            }
            onResult(task)
        }
    }
}