package com.example.todolist.di

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.local.AppDataBase
import com.example.todolist.data.local.ToDoTaskDao
import com.example.todolist.data.local.ToDoTasksLocalDataSource
import com.example.todolist.data.utils.ToDoTaskToToDoTaskDbEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): AppDataBase{
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "database.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideToDoTaskDao(db: AppDataBase) = db.getToDoTaskDao()

    @Provides
    @Singleton
    fun provideToDoTaskToToDoTaskDbEntityMapper(): ToDoTaskToToDoTaskDbEntityMapper{
        return ToDoTaskToToDoTaskDbEntityMapper()
    }

    @Provides
    @Singleton
    fun provideToDoTasksLocalDataSource(
        toDoTaskDao: ToDoTaskDao,
        toDoTaskDbEntityMapper: ToDoTaskToToDoTaskDbEntityMapper,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): ToDoTasksLocalDataSource {
        return ToDoTasksLocalDataSource(toDoTaskDao, toDoTaskDbEntityMapper, dispatcher)
    }

}