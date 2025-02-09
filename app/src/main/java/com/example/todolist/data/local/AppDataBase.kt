package com.example.todolist.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.data.local.entities.ToDoTaskDbEntity

@Database(
    version = 1,
    entities = [
        ToDoTaskDbEntity::class
    ]
)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getToDoTaskDao(): ToDoTaskDao
}