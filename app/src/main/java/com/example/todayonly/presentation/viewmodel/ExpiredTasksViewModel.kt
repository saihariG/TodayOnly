package com.example.todayonly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todayonly.domain.repository.TaskRepository
import com.example.todayonly.presentation.uistates.ExpiredUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpiredTasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExpiredUiState())
    val state : StateFlow<ExpiredUiState> = _state.asStateFlow()

    init {
        loadExpiredTasks()
    }

    fun loadExpiredTasks() {
        viewModelScope.launch {
            try {
                taskRepository.observeExpiredTasks().collect { tasks ->
                    _state.value = ExpiredUiState(
                        tasksGroupedByDay = tasks.groupBy { task ->
                            task.createdDay
                        },
                        isLoading = false,
                        error= null
                    )
                }
            } catch (e: Exception) {
                _state.value = ExpiredUiState(
                    isLoading = false,
                    error = "${e.message} Failed to load expired tasks"
                )
            }
        }
    }
}