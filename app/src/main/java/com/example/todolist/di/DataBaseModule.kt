package com.example.todolist.di

import android.content.Context
import androidx.room.Room
import com.example.todolist.model.room.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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

}