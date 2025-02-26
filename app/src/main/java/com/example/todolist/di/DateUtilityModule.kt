package com.example.todolist.di

import com.example.todolist.common.utils.DateUtility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
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
            it.set(Calendar.HOUR_OF_DAY, 0)
            it.set(Calendar.MINUTE, 0)
            it.set(Calendar.SECOND, 0)
            it.set(Calendar.MILLISECOND, 0)
        }
    }
}