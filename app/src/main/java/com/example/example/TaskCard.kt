package com.example.example

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.example.data.Task

@Composable
fun TaskCard(
    task: Task,
    deleteOnClick: () -> Unit,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Title: " + task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Description: " + task.description,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Priority: " + task.priority,
                    fontSize = 12.sp,

                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Date: " + task.dueDate,
                    fontSize = 12.sp,

                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Status: " + task.isCompleted,
                    fontSize = 12.sp,

                    modifier = Modifier.padding(5.dp)
                )
            }

            IconButton(onClick = {
                deleteOnClick()
            }, modifier = Modifier.size(18.dp)) {
                Icon(Icons.Default.Delete, contentDescription = "Edit")
            }
        }
    }
}
