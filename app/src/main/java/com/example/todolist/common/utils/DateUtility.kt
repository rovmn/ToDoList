package com.example.todolist.common.utils

import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class DateUtility @Inject constructor(
    private val calendar: Calendar
) {

    fun getSelectedDate(year: Int, month: Int, day: Int): Date{
        val tempCalendar = calendar
        tempCalendar.set(year, month, day)
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