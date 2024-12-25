package com.example.todolist.utils

import java.util.Calendar
import java.util.Date

class DateUtility {

    companion object {

        private val calendar = Calendar.getInstance()

        fun getDate(year: Int, month: Int, day: Int): Date{
            val tempCalendar = calendar
            tempCalendar.time = Date(0)
            tempCalendar.set(year, month, day)
            return tempCalendar.time
        }

        fun getCurrentDate() = calendar.time

        fun setDate(selectedDate: Date, selectedTime: Int): Date {
            val tempCalendar = calendar
            tempCalendar.time = selectedDate

            tempCalendar.set(Calendar.HOUR_OF_DAY, selectedTime)

            return tempCalendar.time
        }

    }
}

fun Date.getTimeHours(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.HOUR_OF_DAY)
}