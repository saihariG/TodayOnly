package com.example.todayonly.domain.repository

interface TaskRepository {
    suspend fun addTask(title: String): Long
}