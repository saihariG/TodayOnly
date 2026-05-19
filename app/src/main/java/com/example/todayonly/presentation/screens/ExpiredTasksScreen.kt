package com.example.todayonly.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todayonly.presentation.screens.composables.EmptyExpiredTasks
import com.example.todayonly.presentation.screens.composables.TaskItem
import com.example.todayonly.presentation.viewmodel.ExpiredTasksViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpiredTasksScreen(
    onBack: () -> Unit,
    viewModel: ExpiredTasksViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Expired Tasks"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            if (!state.isLoading && state.tasksGroupedByDay.isEmpty()) {
                EmptyExpiredTasks(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    state.tasksGroupedByDay.forEach { (day, tasks) ->
                        item {
                            Text(text = "Tasks expired on $day")
                        }
                        items(tasks, key = { it.id }) { task ->
                            TaskItem(
                                task = task,
                                onMarkedComplete = { /* read-only */ }
                            )
                        }
                    }
                }
            }
        }
    }
}