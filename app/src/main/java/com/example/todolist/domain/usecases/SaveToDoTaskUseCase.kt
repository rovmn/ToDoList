package com.example.todolist.domain.usecases

import com.example.todolist.domain.models.ToDoTask
import com.example.todolist.domain.repositories.ToDoTaskRepository
import com.example.todolist.common.utils.Result

class SaveToDoTaskUseCase(
    private val toDoTaskRepository: ToDoTaskRepository
) {
    suspend fun saveToDoTask(toDoTask: ToDoTask): Result<Unit> =
        if(toDoTask.id == 0.toLong()) toDoTaskRepository.createToDoTask(toDoTask)
        else toDoTaskRepository.updateToDoTask(toDoTask)
}