package com.example.example

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.example.data.Task
import com.example.example.ui.theme.ExampleTheme
import com.example.example.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val taskViewModel: TaskViewModel = viewModel()
            NavHost(navController = navController, startDestination = "task_list") {
                composable("task_list") {

                    TaskListScreen(taskViewModel = taskViewModel, navController = navController)
                }
                composable("task_detail/{taskId}/{taskTitle}") {
                    val taskTitle = it.arguments?.getString("taskTitle")
                    val taskId = it.arguments?.getString("taskId")!!
                    TaskDetail(
                        taskViewModel = taskViewModel,
                        navController = navController,
                        taskTitle = taskTitle ?: "",
                        taskId = taskId
                    )
                }
            }
        }
    }
}


