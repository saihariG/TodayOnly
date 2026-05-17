package com.example.todayonly.di

import com.example.todayonly.domain.Clock
import com.example.todayonly.domain.ClockImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClockModule {

    @Provides
    @Singleton
    fun provideClock(): Clock = ClockImpl()
}