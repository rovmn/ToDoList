package com.example.todolist.common.utils

import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject

class DateUtility @Inject constructor(
    private val calendar: Calendar
) {

    fun getSelectedDate(year: Int, month: Int, day: Int): Date{
        val tempCalendar = calendar
        tempCalendar.time = Date(0)
        tempCalendar.set(year, month, day)
        return tempCalendar.time
    }

    fun getCurrentDate(): Date {
        calendar.timeZone = TimeZone.getDefault()
        return calendar.time
    }

    fun getSelectedDateWithSelectedTime(selectedDate: Date, selectedTime: Int): Date {
        val tempCalendar = calendar
        tempCalendar.time = selectedDate

        tempCalendar.set(Calendar.HOUR_OF_DAY, selectedTime)

        return tempCalendar.time
    }

    fun getTimeHours(date: Date): Int {
        calendar.time = date
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    companion object {
        const val HOURS_IN_DAY = 24
    }
}