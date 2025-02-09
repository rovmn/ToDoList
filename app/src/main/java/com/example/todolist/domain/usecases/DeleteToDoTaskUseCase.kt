package com.example.todolist.domain.usecases

import com.example.todolist.domain.repositories.ToDoTaskRepository

class DeleteToDoTaskUseCase(
    private val toDoTasksRepository: ToDoTaskRepository
) {
    suspend fun deleteToDoTask(toDoTaskId: Long) =
        toDoTasksRepository.deleteToDoTask(toDoTaskId)
}