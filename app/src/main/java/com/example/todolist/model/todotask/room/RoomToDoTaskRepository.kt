package com.example.todolist.model.todotask.room


import com.example.todolist.di.IoDispatcher
import com.example.todolist.model.ErrorResult
import com.example.todolist.model.PendingResult
import com.example.todolist.model.Result
import com.example.todolist.model.SuccessResult
import com.example.todolist.model.todotask.ToDoTaskRepository
import com.example.todolist.model.todotask.entities.ToDoTask
import com.example.todolist.model.todotask.room.entities.ToDoTaskDbEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class RoomToDoTaskRepository @Inject constructor(
    private val toDoTasksDao: ToDoTaskDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ToDoTaskRepository {

    override suspend fun createToDoTask(toDoTask: ToDoTask): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                PendingResult<Unit>()
                val toDoTaskDbEntity = ToDoTaskDbEntity.fromToDoTask(toDoTask)
                toDoTasksDao.createToDoTask(toDoTaskDbEntity)
                SuccessResult(Unit)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }

    override suspend fun deleteToDoTask(toDoTaskId: Long): Result<Unit> {
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

    override suspend fun getToDoTasks(date: Date): Result<List<ToDoTask>> {
        return withContext(ioDispatcher) {
            try {
                PendingResult<Unit>()
                var toDoTasks = toDoTasksDao.getToDoTasks(date).map {
                        it.toToDoTask()
                }
                SuccessResult(toDoTasks)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }

    override suspend fun updateToDoTask(toDoTask: ToDoTask): Result<Unit> {
        return withContext(ioDispatcher) {
            try {
                toDoTasksDao.updateToDoTask(ToDoTaskDbEntity.fromToDoTask(toDoTask))
                SuccessResult(Unit)
            } catch (e: Exception) {
                ErrorResult(e)
            }
        }
    }
}