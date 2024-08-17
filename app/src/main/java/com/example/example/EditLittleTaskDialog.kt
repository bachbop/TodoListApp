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
import com.example.example.data.LittleTask
import com.example.example.data.Task
import com.example.example.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLittleTaskDialog(
    littleTaskId: Int,
    viewModel: TaskViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    var littleTask by remember {
        mutableStateOf<LittleTask?>(null)
    }
    var inputNameTask by remember {
        mutableStateOf("")
    }
    var inputTime by remember {
        mutableStateOf("")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var empty by remember {
        mutableStateOf("")
    }
    var inputStatus by remember {
        mutableStateOf("Uncompleted")
    }

    LaunchedEffect(littleTaskId) {
        viewModel.getLittleTaskById(littleTaskId) { fetchedTask ->
            fetchedTask?.let {
                littleTask = it
                inputNameTask = it.nameTask
                inputTime = it.time
                inputStatus = it.status
            }
        }
    }

    littleTaskId?.let {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                Button(onClick = {
                    val updateTask = littleTask!!.copy(
                         nameTask = inputNameTask,
                        time = inputTime
                    )
                    viewModel.updateLittleTask(updateTask)
                    onSave()
                }) {
                    Text(text = "Update")
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text(text = "Cancel")
                }
            },
            title = { Text(text = "Edit Task") },
            text = {
                Column {
                    OutlinedTextField(
                        value = inputNameTask,
                        onValueChange = { inputNameTask = it },
                        label = { Text("Title") }
                    )
                    OutlinedTextField(
                        value = inputTime,
                        onValueChange = { inputTime = it },
                        label = { Text("Time") },
                    )
                }
            }

        )
    }
}