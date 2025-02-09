package com.example.todolist.domain.repositories

import com.example.todolist.common.utils.Result
import com.example.todolist.domain.models.ToDoTask
import java.util.Date

interface ToDoTaskRepository {

    suspend fun createToDoTask(toDoTask: ToDoTask): Result<Unit>

    suspend fun deleteToDoTask(toDoTaskId: Long): Result<Unit>

    suspend fun getToDoTasks(date: Date) : Result<List<ToDoTask>>

    suspend fun updateToDoTask(toDoTask: ToDoTask): Result<Unit>
}