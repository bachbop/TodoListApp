package com.example.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.example.data.AppDatabase
import com.example.example.data.LittleTask
import com.example.example.data.LittleTaskDao
import com.example.example.data.Task
import com.example.example.data.TaskDao
import com.example.example.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>
    val allLittleTasks: LiveData<List<LittleTask>>
    private val taskDao: TaskDao = AppDatabase.getDatabase(application).taskDao()
    private val littleTaskDao: LittleTaskDao = AppDatabase.getDatabase(application).littleTaskDao()

    init {
        val taskDao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao, littleTaskDao)
        allTasks = repository.allTasks
        allLittleTasks = repository.allLittleTasks
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

    fun insertLittleTask(littleTask: LittleTask) = viewModelScope.launch {
        repository.insertLittleTask(littleTask)
    }

    fun updateLittleTask(littleTask: LittleTask) = viewModelScope.launch {
        repository.updateLittleTask(littleTask)
    }

    fun deleteLittleTask(littleTask: LittleTask) = viewModelScope.launch {
        repository.deleteLittleTask(littleTask)
    }

    fun getTaskById(taskId: Int, onResult: (Task?) -> Unit) {
        viewModelScope.launch {
            val task = withContext(Dispatchers.IO){
                taskDao.getTaskById(taskId)
            }
            onResult(task)
        }
    }
    fun getLittleTaskByTaskId(taskId: Int): LiveData<List<LittleTask>>{
        return repository.getLittleTaskByTaskId(taskId).asLiveData()
    }

    fun getLittleTaskById(littleTaskId: Int, onResult: (LittleTask?) -> Unit) {
        viewModelScope.launch {
            val littleTask = withContext(Dispatchers.IO){
                littleTaskDao.getLittleTaskById(littleTaskId)
            }
            onResult(littleTask)
        }
    }
}