package com.example.example

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Dialog(
    onDismissRequest: () -> Unit,
    dismissButton:@Composable() () -> Unit,
    confirmButton:@Composable() () -> Unit,
    alertTitle: String,
    textForm:@Composable() () -> Unit
) {
    AlertDialog(
        onDismissRequest = {onDismissRequest()},
        dismissButton = {
            dismissButton()
        },
        confirmButton = {
            confirmButton()
        },
        title = {
            Text(
                text = alertTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(5.dp)
            )
        },
        text = {textForm()},

    )
}