package com.example.todolist.di

import com.example.todolist.domain.repositories.ToDoTaskRepository
import com.example.todolist.data.repositories.ToDoTaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ToDoTaskRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindToDoTaskRepository(
        toDoTaskRepository: ToDoTaskRepositoryImpl
    ): ToDoTaskRepository
}