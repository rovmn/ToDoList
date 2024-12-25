package com.example.todolist.screens.createtodotask.model

import com.example.todolist.model.todotask.entities.ToDoTask
import java.util.Date

sealed class ToDoTaskCreationEvent {
    object OnConfirmButtonClick: ToDoTaskCreationEvent()
    data class TitleChanged(val value: String) : ToDoTaskCreationEvent()
    data class DescriptionChanged(val value: String) : ToDoTaskCreationEvent()
    data class SelectedDateChanged(val value: Date) : ToDoTaskCreationEvent()
    data class SelectedTimeChanged(val value: Int) : ToDoTaskCreationEvent()
    data class ToDoTaskReceived(val value: ToDoTask) : ToDoTaskCreationEvent()
}