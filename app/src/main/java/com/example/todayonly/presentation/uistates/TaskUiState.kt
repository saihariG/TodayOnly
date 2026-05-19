package com.example.todayonly.presentation.uistates

import com.example.todayonly.domain.model.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
