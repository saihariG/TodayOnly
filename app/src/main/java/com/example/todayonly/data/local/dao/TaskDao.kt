package com.example.todayonly.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todayonly.data.local.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    // Returning Flow lets the UI observe changes reactively
    @Query("SELECT * from tasks WHERE createdDay = :day ORDER BY id DESC")
    fun observeTasks(day: Long): Flow<List<TaskEntity>>

    @Query("SELECT * from tasks WHERE createdDay < :day AND isCompleted = 1 ORDER BY id DESC")
    fun observeExpiredTasks(day: Long): Flow<List<TaskEntity>>

    @Update
    suspend fun markTaskComplete(task: TaskEntity)

    @Query("SELECT * from tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity?
}