package com.example.example.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
    @Transaction
    @Query("SELECT * FROM tasks ORDER BY time ASC")
    fun getAllTasks(): LiveData<List<Task>>
    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId LIMIT 1")
    fun getTaskById(taskId: Int?): Task?

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskWithLittleTask(taskId: Int?) : TaskWithLittleTasks
}

@Dao
interface LittleTaskDao {
    @Insert
    suspend fun insertLittleTask(littleTask: LittleTask)

    @Update
    suspend fun updateLittleTask(littleTask: LittleTask)

    @Delete
    suspend fun deleteLittleTask(littleTask: LittleTask)
    @Transaction
    @Query("SELECT * FROM little_task ORDER BY time ASC")
    fun getAllLittleTasks(): LiveData<List<LittleTask>>
    @Transaction
    @Query("SELECT * FROM little_task WHERE taskId = :taskId")
    fun getLittleTaskByTaskId(taskId: Int?): Flow<List<LittleTask>>

    @Transaction
    @Query("SELECT * FROM little_task WHERE id = :littleTaskId LIMIT 1")
    fun getLittleTaskById(littleTaskId: Int?): LittleTask?

}
