package com.example.todolist.model.todotask.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.todolist.model.todotask.entities.ToDoTask
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
) {

    fun toToDoTask() : ToDoTask = ToDoTask(
        id = id,
        title = title,
        description = description,
        toDoTaskBegin = toDoTaskBegin,
        toDoTaskEnd = toDoTaskEnd
    )

    companion object {
        fun fromToDoTask(toDoTask: ToDoTask): ToDoTaskDbEntity = ToDoTaskDbEntity(
            id = toDoTask.id,
            title = toDoTask.title,
            description = toDoTask.description,
            toDoTaskBegin = toDoTask.toDoTaskBegin,
            toDoTaskEnd = toDoTask.toDoTaskEnd
        )
    }
}