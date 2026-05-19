package com.example.todayonly.presentation.uistates

import com.example.todayonly.domain.model.Task

data class ExpiredUiState(
    val tasksGroupedByDay: Map<Long, List<Task>> = emptyMap(),
    val isLoading: Boolean = true,
    val error: String? = null
)