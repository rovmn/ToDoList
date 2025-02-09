package com.example.todolist.data.repositories

import com.example.todolist.data.local.ToDoTasksLocalDataSource
import com.example.todolist.domain.models.ToDoTask
import com.example.todolist.domain.repositories.ToDoTaskRepository
import com.example.todolist.common.utils.Result
import java.util.Date
import javax.inject.Inject

class ToDoTaskRepositoryImpl @Inject constructor(
    private val toDoTasksLocalDataSource: ToDoTasksLocalDataSource
) : ToDoTaskRepository {
    override suspend fun createToDoTask(toDoTask: ToDoTask): Result<Unit> =
        toDoTasksLocalDataSource.createToDoTask(toDoTask)

    override suspend fun deleteToDoTask(toDoTaskId: Long): Result<Unit> =
        toDoTasksLocalDataSource.deleteToDoTask(toDoTaskId)

    override suspend fun getToDoTasks(date: Date): Result<List<ToDoTask>> =
        toDoTasksLocalDataSource.getToDoTasks(date)

    override suspend fun updateToDoTask(toDoTask: ToDoTask): Result<Unit> =
        toDoTasksLocalDataSource.updateToDoTask(toDoTask)
}