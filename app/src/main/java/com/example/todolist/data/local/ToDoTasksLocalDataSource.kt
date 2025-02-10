package com.example.todolist.data.local

import com.example.todolist.data.utils.ToDoTaskToToDoTaskDbEntityMapper
import com.example.todolist.di.IoDispatcher
import com.example.todolist.common.utils.ErrorResult
import com.example.todolist.common.utils.PendingResult
import com.example.todolist.common.utils.Result
import com.example.todolist.common.utils.SuccessResult
import com.example.todolist.domain.repositories.ToDoTaskRepository
import com.example.todolist.domain.models.ToDoTask
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class ToDoTasksLocalDataSource @Inject constructor(
    private val toDoTasksDao: ToDoTaskDao,
    private val toDoTaskDbEntityMapper: ToDoTaskToToDoTaskDbEntityMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun createToDoTask(toDoTask: ToDoTask): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                PendingResult<Unit>()
                val toDoTaskDbEntity = toDoTaskDbEntityMapper.map(toDoTask)
                toDoTasksDao.createToDoTask(toDoTaskDbEntity)
                SuccessResult(Unit)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }

    suspend fun deleteToDoTask(toDoTaskId: Long): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                PendingResult<Unit>()
                toDoTasksDao.deleteToDoTask(toDoTaskId)
                SuccessResult(Unit)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }

    suspend fun getToDoTasks(startOfDay: Date, endOfDay: Date): Result<List<ToDoTask>> {
        return withContext(ioDispatcher) {
            try {
                PendingResult<Unit>()
                var toDoTasks = toDoTasksDao.getToDoTasks(startOfDay, endOfDay).map { toDoTaskDbEntity ->
                    toDoTaskDbEntityMapper.reverseMap(toDoTaskDbEntity)
                }
                SuccessResult(toDoTasks)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }

    suspend fun updateToDoTask(toDoTask: ToDoTask): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                toDoTasksDao.updateToDoTask(toDoTaskDbEntityMapper.map(toDoTask))
                SuccessResult(Unit)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }
}