package com.example.todolist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.data.local.entities.ToDoTaskDbEntity
import java.util.Date

@Dao
interface ToDoTaskDao {

    @Query("SELECT * FROM tasks WHERE task_begin BETWEEN :startOfDay AND :endOfDay ")
    suspend fun getToDoTasks(startOfDay: Date, endOfDay: Date): List<ToDoTaskDbEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateToDoTask(toDoTaskDbEntity: ToDoTaskDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createToDoTask(toDoTaskDbEntity: ToDoTaskDbEntity)

    @Query("DELETE FROM tasks WHERE id = :toDoTaskId")
    suspend fun deleteToDoTask(toDoTaskId: Long)
}