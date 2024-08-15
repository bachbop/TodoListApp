package com.example.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val priority: String, // 1 - Low, 2 - Medium, 3 - High
    val dueDate: String, // Store as a timestamp
    val isCompleted: String
)
