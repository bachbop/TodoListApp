package com.example.example

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.example.data.LittleTask
import com.example.example.data.Task
import com.example.example.viewmodel.TaskViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun TaskDetail(taskViewModel: TaskViewModel, navController: NavController, taskTitle: String, taskId: String) {
    val littleTaskById = taskId.toInt()
    val listLittleTask = taskViewModel.getLittleTaskByTaskId(littleTaskById).observeAsState(listOf()).value
    var littleTaskId by remember {
        mutableStateOf<Int?>(null)
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                        Text(text = taskTitle)
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        content = {innerPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                items(listLittleTask) { allLittleTask ->
                    TaskDetailCard(
                        littleTask = allLittleTask,
                        deleteOnClick = { taskViewModel.deleteLittleTask(allLittleTask) },
                        editOnClick = {
                            littleTaskId = allLittleTask.id
                        })
                }
            }
            littleTaskId?.let {
                    selectTask ->
                EditLittleTaskDialog(
                    littleTaskId = selectTask,
                    viewModel = taskViewModel,
                    onDismiss = { littleTaskId = null },
                    onSave = {littleTaskId = null})
            }
        }
    )

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                    inputNameTask = empty
                    inputTime = empty
                }) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                if (inputNameTask.isNotEmpty() &&
                    inputTime.isNotEmpty()
                ) {
                    Button(onClick = {
                        taskViewModel.insertLittleTask(
                            LittleTask(
                                nameTask = inputNameTask,
                                time = inputTime,
                                status = inputStatus,
                                taskId = taskId.toInt()
                            )
                        )
                        showDialog = false
                        inputNameTask = empty
                        inputTime = empty
                        inputStatus = "Uncompleted"
                    }) {
                        Text(text = "Save")
                    }
                }
            },
            alertTitle = "Add Task",
            textForm = {
                Column {
                    Spacer(modifier = Modifier.height(15.dp))
                    OutlinedTextField(
                        value = inputNameTask,
                        onValueChange = { inputNameTask = it },
                        label = {
                            Text(text = "Title")
                        },
                        placeholder = { Text(text = "Title") }
                    )
                    OutlinedTextField(
                        value = inputTime,
                        onValueChange = { inputTime = it },
                        label = {
                            Text(text = "Time")
                        },
                        placeholder = { Text(text = "Time") }
                    )

                }
            }
        )
    }
}
