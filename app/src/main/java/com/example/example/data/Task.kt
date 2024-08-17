package com.example.example.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val title: String,
    val description: String,
    val priority: String, // 1 - Low, 2 - Medium, 3 - High
    val time: String,
    val isCompleted: String
)

@Entity(
    tableName = "little_task",
    foreignKeys = [ForeignKey(
        entity = Task::class,
        parentColumns = ["id"],
        childColumns = ["taskId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["taskId"])]
)
data class LittleTask(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val nameTask: String,
    val time: String,
    var status: String,
    val taskId: Int
)

data class TaskWithLittleTasks(
    @Embedded val task : Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val littleTasks : List<LittleTask>
)