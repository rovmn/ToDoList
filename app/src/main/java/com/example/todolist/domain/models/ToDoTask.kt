package com.example.todolist.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ToDoTask(
    val id: Long = 0,
    val title: String,
    val description: String,
    val toDoTaskBegin: Date,
    val toDoTaskEnd: Date
): Parcelable