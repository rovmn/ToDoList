package com.example.todolist.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist.model.todotask.room.ToDoTaskDao
import com.example.todolist.model.todotask.room.entities.ToDoTaskDbEntity

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