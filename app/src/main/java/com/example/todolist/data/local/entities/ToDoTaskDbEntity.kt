package com.example.todolist.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "tasks",
    indices = [
        Index("task_begin", unique = true)
    ]
)
data class ToDoTaskDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val description: String,
    @ColumnInfo(name = "task_begin") val toDoTaskBegin: Date,
    @ColumnInfo(name = "task_end") val toDoTaskEnd: Date
)