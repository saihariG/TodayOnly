package com.example.todayonly.presentation.screens.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todayonly.domain.model.Task

@Composable
fun TaskItem(
    task: Task,
    onMarkedComplete : () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

    }

    Text(
        text = task.title
    )
}