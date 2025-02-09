package com.example.todolist.di

import com.example.todolist.common.utils.DateUtility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DateUtilityModule(

) {

    @Provides
    @Singleton
    fun provideDateUtility(calendar: Calendar): DateUtility {
        return DateUtility(calendar)
    }

    @Provides
    @Singleton
    fun provideCalendar(): Calendar {
        return Calendar.getInstance().also {
            it.timeZone = TimeZone.getDefault()
        }
    }
}