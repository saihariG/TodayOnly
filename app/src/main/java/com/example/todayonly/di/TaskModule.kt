package com.example.todayonly.di

import com.example.todayonly.data.repository.TaskRepositoryImpl
import com.example.todayonly.domain.repository.TaskRepository
import com.example.todayonly.notification.NotificationScheduler
import com.example.todayonly.notification.NotificationSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskModule {

    @Binds
    abstract fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository

    @Binds
    abstract fun bindNotificationScheduler(notificationSchedulerImpl: NotificationSchedulerImpl): NotificationScheduler
}