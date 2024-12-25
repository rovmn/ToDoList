package com.example.todolist.utils

interface EventHandler<E> {
    fun obtainEvent(event: E)
}