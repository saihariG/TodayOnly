package com.example.todayonly.data.repository

import com.example.todayonly.data.local.dao.TaskDao
import com.example.todayonly.data.local.entities.TaskEntity
import com.example.todayonly.domain.Clock
import com.example.todayonly.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

// Single Source of Truth for data
@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao,
    private val clock: Clock
) : TaskRepository {

    override suspend fun addTask(title: String): Long {
        val task = TaskEntity(
            title = title,
            createdDay = clock.today().toEpochDay()
        )

        return dao.insertTask(task)
    }

}