package com.example.todayonly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todayonly.data.local.dao.TaskDao
import com.example.todayonly.data.local.entities.TaskEntity

@Database(
    entities = [TaskEntity::class], // creates a database with these tables
    version = 1,
    exportSchema = false // setting true saves the schema
)
abstract class AppDatabase : RoomDatabase() {

    // entry points to interact with tables
    abstract fun taskDao(): TaskDao
}