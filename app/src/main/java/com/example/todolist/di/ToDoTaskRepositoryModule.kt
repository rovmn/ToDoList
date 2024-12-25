package com.example.todolist.di

import com.example.todolist.model.todotask.ToDoTaskRepository
import com.example.todolist.model.todotask.room.RoomToDoTaskRepository
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
        toDoTaskRepository: RoomToDoTaskRepository
    ): ToDoTaskRepository
}