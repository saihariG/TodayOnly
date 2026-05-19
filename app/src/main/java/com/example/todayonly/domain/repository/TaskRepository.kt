package com.example.todayonly.domain.repository

import com.example.todayonly.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(title: String, reminderMillis: Long? = null): Long

    fun observeTasks(): Flow<List<Task>>

    fun observeExpiredTasks(): Flow<List<Task>>

    suspend fun markTaskComplete(id: Long, isCompleted: Boolean)
}