package com.example.todayonly.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.todayonly.data.local.entities.TaskEntity

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: TaskEntity): Long
}