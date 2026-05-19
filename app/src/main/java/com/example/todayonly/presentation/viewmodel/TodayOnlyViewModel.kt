package com.example.todayonly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todayonly.domain.Clock
import com.example.todayonly.domain.model.Task
import com.example.todayonly.domain.repository.TaskRepository
import com.example.todayonly.presentation.UiEvent
import com.example.todayonly.presentation.uistates.TaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayOnlyViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val clock: Clock
) : ViewModel() {

    private val _state = MutableStateFlow(TaskUiState())
    val state : StateFlow<TaskUiState> = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow() // for one-shot events like snackbars

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            try {
                taskRepository.observeTasks().collect { tasks ->
                    _state.value = TaskUiState(
                        tasks = tasks,
                        error = null,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = TaskUiState(
                    error = "${e.message} Failed to load tasks",
                    isLoading = false
                )
            }
        }
    }

    fun addTask(title: String) {
        viewModelScope.launch {
            val trimmedTitle = title.trim()

            if(trimmedTitle.isBlank()) {
                _uiEvent.emit(UiEvent.ShowSnackBar("Title cannot be empty"))
                return@launch
            }

            try {
                taskRepository.addTask(title)
                _uiEvent.emit(UiEvent.ShowSnackBar("Task created"))
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackBar(e.message ?: "Failed to create task"))
            }
        }
    }

    fun markComplete(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.markTaskComplete(task.id, !task.isCompleted)
                if(task.isCompleted) {
                    _uiEvent.emit(UiEvent.ShowSnackBar("Task marked as incomplete"))
                } else {
                    _uiEvent.emit(UiEvent.ShowSnackBar("Task marked as completed"))
                }
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowSnackBar(e.message ?: "Failed to mark task complete"))
            }
        }
    }

}