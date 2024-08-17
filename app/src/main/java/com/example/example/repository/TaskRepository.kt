package com.example.example.repository

import androidx.lifecycle.LiveData
import com.example.example.data.LittleTask
import com.example.example.data.LittleTaskDao
import com.example.example.data.Task
import com.example.example.data.TaskDao
import com.example.example.data.TaskWithLittleTasks
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao, private val littleTaskDao: LittleTaskDao) {
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()
    val allLittleTasks: LiveData<List<LittleTask>> = littleTaskDao.getAllLittleTasks()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun insertLittleTask(littleTask: LittleTask) {
        littleTaskDao.insertLittleTask(littleTask)
    }

    suspend fun updateLittleTask(littleTask: LittleTask) {
        littleTaskDao.updateLittleTask(littleTask)
    }

    suspend fun deleteLittleTask(littleTask: LittleTask) {
        littleTaskDao.deleteLittleTask(littleTask)
    }

    suspend fun getTaskWithLittleTasks(taskId: Int): TaskWithLittleTasks {
        return taskDao.getTaskWithLittleTask(taskId)
    }

    fun getLittleTaskByTaskId(taskId: Int): Flow<List<LittleTask>>{
        return littleTaskDao.getLittleTaskByTaskId(taskId)
    }

}