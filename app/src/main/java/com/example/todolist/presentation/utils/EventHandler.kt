package com.example.todolist.presentation.utils

interface EventHandler<E> {
    fun obtainEvent(event: E)
}