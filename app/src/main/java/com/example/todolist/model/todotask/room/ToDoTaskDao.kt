package com.example.todolist.model.todotask.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.model.todotask.room.entities.ToDoTaskDbEntity
import java.util.Date

@Dao
interface ToDoTaskDao {

    @Query("SELECT * FROM tasks " +
            "WHERE DATE(task_begin, 'unixepoch') = DATE(:date, 'unixepoch') " +
            "ORDER BY task_begin")
    suspend fun getToDoTasks(date: Date): List<ToDoTaskDbEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateToDoTask(toDoTaskDbEntity: ToDoTaskDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createToDoTask(toDoTaskDbEntity: ToDoTaskDbEntity)

    @Query("DELETE FROM tasks WHERE id = :toDoTaskId")
    suspend fun deleteToDoTask(toDoTaskId: Long)
}