package com.example.todolist.domain.usecases

import com.example.todolist.domain.models.ToDoTask
import com.example.todolist.domain.repositories.ToDoTaskRepository
import com.example.todolist.common.utils.Result
import java.util.Date

class GetToDoTasksByDateUseCase(
    private val toDoTasksRepository: ToDoTaskRepository
) {
    suspend fun getToDoTasksByDate(startOfDay: Date, endOfDay: Date): Result<List<ToDoTask>> =
        toDoTasksRepository.getToDoTasks(startOfDay, endOfDay)
}