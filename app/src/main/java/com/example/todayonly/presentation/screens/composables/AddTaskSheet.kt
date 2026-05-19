package com.example.todayonly.presentation.screens.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskSheet(
    onDismiss: () -> Unit,
    onSubmit: (title: String, reminderMillis: Long?) -> Unit,
    onReminderSet: (hour: Int, minute: Int) -> Unit
) {

    var title by remember { mutableStateOf("") }

    var showTimePicker by remember { mutableStateOf(false) }
    var pickedTime by remember { mutableStateOf<LocalTime?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "New task for today",
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Outlined.Close, contentDescription = "Close")
                }
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("What do you want to get done?") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AssistChip(
                    onClick = { showTimePicker = true },
                    label = {
                        Text(
                            if (pickedTime == null) {
                                "Set a reminder"
                            } else {
                                "At ${pickedTime!!.hour}:${pickedTime!!.minute}"
                            }
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.DateRange,
                            contentDescription = "Set reminder",
                            modifier = Modifier.width(12.dp),
                        )
                    }
                )

                if (pickedTime != null) {
                    Spacer(Modifier.width(8.dp))
                    AssistChip(
                        onClick = { pickedTime = null },
                        label = { Text("Clear") }
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            Button(
                onClick = {
                    // set reminder
                    onSubmit(title.trim(), 1L)
                },
                enabled = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Add")
            }
        }

    }

    if(showTimePicker) {

        val timePickerState = rememberTimePickerState(
            initialHour = LocalTime.now().hour,
            initialMinute = LocalTime.now().minute,
            is24Hour = false
        )

        TimePickerDialog(
            onDismiss = {
                pickedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                showTimePicker = false
                        },
            onConfirm = {
                pickedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                showTimePicker = false
                onReminderSet(
                    timePickerState.hour,
                    timePickerState.minute
                )
            }
        ) {
            TimePicker(
                state = timePickerState
            )
        }
    }

}