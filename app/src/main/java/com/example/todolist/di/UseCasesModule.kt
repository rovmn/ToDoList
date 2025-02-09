package com.example.todolist.di

import com.example.todolist.domain.repositories.ToDoTaskRepository
import com.example.todolist.domain.usecases.DeleteToDoTaskUseCase
import com.example.todolist.domain.usecases.GetToDoTasksByDateUseCase
import com.example.todolist.domain.usecases.SaveToDoTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {

    @Provides
    @Singleton
    fun provideGetToDoTasksByDateUseCase(repository: ToDoTaskRepository): GetToDoTasksByDateUseCase {
        return GetToDoTasksByDateUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteToDoTaskUseCase(repository: ToDoTaskRepository): DeleteToDoTaskUseCase {
        return DeleteToDoTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveToDoTaskUseCase(repository: ToDoTaskRepository): SaveToDoTaskUseCase {
        return SaveToDoTaskUseCase(repository)
    }

}