package com.example.example

import androidx.compose.foundation.layout.Column
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.example.data.Task
import com.example.example.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDialog(
    taskId: Int,
    viewModel: TaskViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    var task by remember {
        mutableStateOf<Task?>(null)
    }
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var important by remember {
        mutableStateOf("")
    }
    var time by remember {
        mutableStateOf("")
    }
    var isComplete by remember {
        mutableStateOf("")
    }
    val levelImportant = listOf("Low", "Medium", "High")
    val status = listOf("Completed", "Uncompleted")

    LaunchedEffect(taskId) {
        viewModel.getTaskById(taskId) {
            fetchedTask ->
            fetchedTask?.let {
                task = it
                title = it.title
                description = it.description.toString()
                important = it.priority
                time = it.time
                isComplete = it.isCompleted
            }
        }
    }

    taskId?.let {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                            Button(onClick = {
                                val updateTask = task!!.copy(
                                    title = title,
                                    description = description,
                                    priority = important,
                                    time = time,
                                    isCompleted = isComplete
                                )
                                viewModel.update(updateTask)
                                onSave()
                            }) {
                                Text(text = "Update")
                            }
            },
            dismissButton = { Button(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }},
            title = { Text(text = "Edit Task") },
            text = {
                Column {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title") }
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                    )
                    important = DropDownMenu(options = levelImportant, label = important)
                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("Time") },
                    )
                    isComplete = DropDownMenu(options = status, label = isComplete)
                }
            }

        )
    }
}