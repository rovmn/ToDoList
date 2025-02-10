package com.example.todolist.common.utils

import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class DateUtility @Inject constructor(
    private val calendar: Calendar
) {

    fun getSelectedDate(year: Int, month: Int, day: Int): Date{
        val tempCalendar = calendar
        tempCalendar.set(year, month, day, 0, 0)
        return tempCalendar.time
    }

    fun getStartOfDay(date: Date): Date {
        val tempCalendar = calendar
        tempCalendar.time = date
        return tempCalendar.time
    }

    fun getEndOfDay(date: Date): Date {
        val tempCalendar = calendar
        tempCalendar.time = date
        tempCalendar.set(Calendar.HOUR_OF_DAY, 23)
        tempCalendar.set(Calendar.MINUTE, 59)
        tempCalendar.set(Calendar.SECOND, 59)
        tempCalendar.set(Calendar.MILLISECOND, 999)
        return tempCalendar.time
    }

    fun getCurrentDate(): Date = calendar.time

    fun getSelectedDateWithSelectedTime(selectedDate: Date, selectedTime: Int): Date {
        val tempCalendar = calendar
        tempCalendar.time = selectedDate

        tempCalendar.set(Calendar.HOUR_OF_DAY, selectedTime)

        return tempCalendar.time
    }

    fun getTimeHours(date: Date): Int {
        val tempCalendar = calendar
        tempCalendar.time = date
        return tempCalendar.get(Calendar.HOUR_OF_DAY)
    }

    companion object {
        const val HOURS_IN_DAY = 24
    }
}