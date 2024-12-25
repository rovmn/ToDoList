package com.example.todolist.model.room

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value * 1000)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return (date.time / 1000).toInt().toLong()
    }
}