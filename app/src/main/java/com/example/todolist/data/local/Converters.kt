package com.example.todolist.data.local

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun timestampToDate(value: Long): Date {
        return Date(value * 1000)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return (date.time / 1000)
    }
}