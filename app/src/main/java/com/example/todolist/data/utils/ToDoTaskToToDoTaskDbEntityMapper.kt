package com.example.todolist.data.utils

import com.example.todolist.data.local.entities.ToDoTaskDbEntity
import com.example.todolist.domain.models.ToDoTask

class ToDoTaskToToDoTaskDbEntityMapper {

    fun map(toDoTask: ToDoTask) : ToDoTaskDbEntity {
        return ToDoTaskDbEntity(
                id = toDoTask.id,
                title = toDoTask.title,
                description = toDoTask.description,
                toDoTaskBegin = toDoTask.toDoTaskBegin,
                toDoTaskEnd = toDoTask.toDoTaskEnd
            )
    }

    fun reverseMap(toDoTaskDbEntity: ToDoTaskDbEntity): ToDoTask {
        return ToDoTask(
                id = toDoTaskDbEntity.id,
                title = toDoTaskDbEntity.title,
                description = toDoTaskDbEntity.description,
                toDoTaskBegin = toDoTaskDbEntity.toDoTaskBegin,
                toDoTaskEnd = toDoTaskDbEntity.toDoTaskEnd
            )
    }
}