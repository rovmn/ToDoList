package com.example.todolist.presentation.models

import com.example.todolist.common.utils.EmptyResult
import com.example.todolist.common.utils.Result
import java.util.Date

data class ToDoTaskCreationViewState(
    val toDoTaskId: Long? = null,
    val toDoTaskTitle: String = "",
    val toDoTaskDescription: String = "",
    val selectedDate: Date = Date(0),
    val selectedTime: Int = 0,
    val wheelTimePickerRows: List<String> = emptyList<String>(),
    val isSaveInProgress: Boolean = false,
    val isToDoTaskSaved: Result<Unit> = EmptyResult<Unit>()
)
