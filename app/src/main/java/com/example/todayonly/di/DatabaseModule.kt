package com.example.todayonly.di

import android.content.Context
import androidx.room.Room
import com.example.todayonly.data.local.TodayOnlyDatabase
import com.example.todayonly.data.local.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    // Hilt provides the application context automatically
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): TodayOnlyDatabase {
        return Room.databaseBuilder(
            context,
            TodayOnlyDatabase::class.java,
            "today_only_db"
        ).build()
    }

    @Provides
    fun provideTaskDao(todayOnlyDatabase: TodayOnlyDatabase) : TaskDao = todayOnlyDatabase.taskDao()
}