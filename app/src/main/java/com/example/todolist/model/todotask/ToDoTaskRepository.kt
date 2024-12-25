package com.example.todolist.model.todotask

import com.example.todolist.model.Result
import com.example.todolist.model.todotask.entities.ToDoTask
import java.util.Date

interface ToDoTaskRepository {

    suspend fun createToDoTask(toDoTask: ToDoTask): Result<Unit>

    suspend fun deleteToDoTask(toDoTaskId: Long): Result<Unit>

    suspend fun getToDoTasks(date: Date) : Result<List<ToDoTask>>

    suspend fun updateToDoTask(toDoTask: ToDoTask): Result<Unit>
}