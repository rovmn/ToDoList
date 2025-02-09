package com.example.todolist.common.utils

sealed class Result<T>

class PendingResult<T> : Result<T>()

class EmptyResult<T> : Result<T>()

class SuccessResult<T>(
    val data: T
) : Result<T>()

class ErrorResult<T>(
    val exception: Exception
) : Result<T>()