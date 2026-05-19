package com.example.todayonly.data.repository

import com.example.todayonly.data.local.dao.TaskDao
import com.example.todayonly.data.local.entities.TaskEntity
import com.example.todayonly.domain.Clock
import com.example.todayonly.domain.model.Task
import com.example.todayonly.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Single Source of Truth for data
@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao,
    private val clock: Clock
) : TaskRepository {

    override suspend fun addTask(title: String, reminderMillis: Long?): Long {
        val task = TaskEntity(
            title = title,
            createdDay = clock.today().toEpochDay(),
            reminderMillis = reminderMillis
        )

        return dao.insertTask(task)
    }

    override fun observeTasks(): Flow<List<Task>> {
        return dao.observeTasks(clock.today().toEpochDay()).map {
            it.map { entity ->
                Task(
                    id = entity.id,
                    title = entity.title,
                    isCompleted = entity.isCompleted,
                    createdDay = entity.createdDay,
                    reminderMillis = entity.reminderMillis
                )
            }
        }
    }

    override fun observeExpiredTasks(): Flow<List<Task>> {
        return dao.observeExpiredTasks(clock.today().toEpochDay()).map {
            it.map { entity ->
                Task(
                    id = entity.id,
                    title = entity.title,
                    isCompleted = entity.isCompleted,
                    createdDay = entity.createdDay,
                    reminderMillis = entity.reminderMillis
                )
            }
        }
    }

    override suspend fun markTaskComplete(id: Long, isCompleted: Boolean) {
        val task = dao.getTaskById(id) ?: return
        dao.markTaskComplete(task.copy(isCompleted = isCompleted))
    }

}