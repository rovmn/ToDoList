package com.example.todolist.presentation.utils

import com.example.todolist.domain.models.ToDoTask
import java.util.Date

sealed class ToDoTaskCreationEvent {
    object OnConfirmButtonClick: ToDoTaskCreationEvent()
    data class TitleChanged(val value: String) : ToDoTaskCreationEvent()
    data class DescriptionChanged(val value: String) : ToDoTaskCreationEvent()
    data class SelectedDateChanged(val value: Date) : ToDoTaskCreationEvent()
    data class SelectedTimeChanged(val value: Int) : ToDoTaskCreationEvent()
    data class ToDoTaskReceived(val value: ToDoTask) : ToDoTaskCreationEvent()
}