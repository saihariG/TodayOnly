package com.example.todayonly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todayonly.domain.Clock
import com.example.todayonly.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayOnlyViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val clock: Clock
) : ViewModel() {

    init {
        loadTasks()
    }

    fun loadTasks() {

    }

    fun addTask(title: String) {
        viewModelScope.launch {
            val id = taskRepository.addTask(title)
        }
    }

    fun deleteTask() {

    }

    fun markComplete() {

    }

}