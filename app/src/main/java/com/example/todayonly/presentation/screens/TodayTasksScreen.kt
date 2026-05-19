package com.example.todayonly.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todayonly.presentation.uistates.UiEvent
import com.example.todayonly.presentation.screens.composables.AddTaskSheet
import com.example.todayonly.presentation.screens.composables.EmptyScreen
import com.example.todayonly.presentation.screens.composables.TaskItem
import com.example.todayonly.presentation.viewmodel.TodayOnlyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayTasksScreen(
    viewModel: TodayOnlyViewModel = hiltViewModel(),
    onOpenExpiredTasks: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    var showAddTaskSheet by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(
        viewModel
    ) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackBar -> {
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel,
                        withDismissAction = event.actionLabel == null
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        event.onAction?.invoke()
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "TodayOnly",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onOpenExpiredTasks) {
                        Icon(Icons.Outlined.List, contentDescription = "Expired Tasks")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddTaskSheet = true },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Add task") }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }

                state.error?.isNotBlank() == true -> {
                    Text(state.error!!)
                }

                state.tasks.isEmpty() -> {
                    EmptyScreen(Modifier.align(Alignment.Center))
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.tasks, key = { it.id } ) { task ->
                            TaskItem(
                                task = task,
                                onMarkedComplete = { viewModel.markComplete(task) }
                            )
                        }
                    }
                }

            }
        }

    }

    if(showAddTaskSheet) {
        AddTaskSheet(
            onDismiss = { showAddTaskSheet = false },
            onSubmit = { title, reminder ->
                viewModel.addTask(title)
                showAddTaskSheet = false
            },
            onReminderSet = { hour, minute ->

            }
        )
    }

}