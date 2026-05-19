package com.example.todayonly.presentation.screens.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todayonly.domain.model.Task
import com.example.todayonly.formatReminderTime

@Composable
fun TaskItem(
    task: Task,
    onMarkedComplete : () -> Unit
) {

    val haptic = LocalHapticFeedback.current

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(20.dp)
                .border(
                    width = if(task.isCompleted) 0.5.dp else 1.5.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onMarkedComplete()
                },
            contentAlignment = Alignment.Center
        ) {
            if(task.isCompleted) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Complete",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(Modifier.width(8.dp))

        Column {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = if(task.isCompleted) TextDecoration.LineThrough else null
                )
            )

            Spacer(Modifier.width(4.dp))

            task.reminderMillis?.let {
                Spacer(Modifier.width(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(Modifier.width(2.dp))

                    Text(
                        text = formatReminderTime(it),
                        style = MaterialTheme.typography.labelSmall
                    )

                }
            }
        }
    }
}