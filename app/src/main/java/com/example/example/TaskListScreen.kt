package com.example.example

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.example.data.Task
import com.example.example.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskListScreen(
    taskViewModel: TaskViewModel,
) {
    val tasks by taskViewModel.allTasks.observeAsState(listOf())
    var taskById by remember {
        mutableStateOf<Int?>(null)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }
    var showUpdateDialog by remember {
        mutableStateOf(false)
    }
    var inputTitle by remember { mutableStateOf("") }
    var inputDescription by remember { mutableStateOf("") }
    var inputImportant by remember { mutableStateOf("") }
    var inputDate by remember { mutableStateOf("") }
    var inputComplete by remember { mutableStateOf("") }
    val empty by remember { mutableStateOf("") }
    val levelImportant = listOf("Low", "Medium", "High")
    val status = listOf("Completed", "Uncompleted")
    val pickDate =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("To-Do List") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) {
        LazyColumn {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    deleteOnClick = {taskViewModel.delete(task) },
                    onClick = {taskById = task.id }
                )
            }
        }
        taskById?.let {
                selectTask ->
            EditDialog(
                taskId = selectTask,
                viewModel = taskViewModel,
                onDismiss = { taskById = null },
                onSave = {taskById = null})
        }

    }
    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                    inputTitle = empty
                    inputDescription = empty
                    inputImportant = empty
                    inputDate = empty
                    inputComplete = empty

                }) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                if (inputTitle.isNotEmpty() &&
                    inputDescription.isNotEmpty() &&
                    inputImportant.isNotEmpty() &&
                    inputDate.isNotEmpty() &&
                    inputComplete.isNotEmpty()
                ) {
                    Button(onClick = {
                        taskViewModel.insert(
                            Task(
                                0,
                                inputTitle,
                                inputDescription,
                                inputImportant,
                                inputDate,
                                inputComplete
                            )
                        )
                        showDialog = false
                        inputTitle = empty
                        inputDescription = empty
                        inputImportant = empty
                        inputDate = empty
                        inputComplete = empty
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
                        value = inputTitle,
                        onValueChange = { inputTitle = it },
                        label = {
                            Text(text = "Title")
                        },
                        placeholder = { Text(text = "Title") }
                    )
                    OutlinedTextField(
                        value = inputDescription,
                        onValueChange = { inputDescription = it },
                        label = {
                            Text(text = "Description")
                        },
                        placeholder = { Text(text = "Description") }
                    )
                    inputImportant =
                        DropDownMenu(options = levelImportant, label = "Level Important")
                    inputDate = DropDownMenu(options = pickDate, label = "Date")
                    inputComplete = DropDownMenu(options = status, label = "Status")
                }
            }
        )
    };
}



