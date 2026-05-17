package com.example.todayonly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todayonly.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodayOnlyViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    init {
        loadTasks()
    }

    fun loadTasks() {

    }

    fun addTask() {

    }

    fun deleteTask() {

    }

    fun markComplete() {

    }

}