package com.example.todolist.screens.createtodotask.model

import com.example.todolist.model.EmptyResult
import com.example.todolist.model.Result
import com.example.todolist.utils.DateUtility
import java.util.Date

data class ToDoTaskCreationViewState(
    val toDoTaskId: Long? = null,
    val toDoTaskTitle: String = "",
    val toDoTaskDescription: String = "",
    val selectedDate: Date = DateUtility.getCurrentDate(),
    val selectedTime: Int = 0,
    val isSaveInProgress: Boolean = false,
    val isToDoTaskSaved: Result<Unit> = EmptyResult<Unit>()
)
