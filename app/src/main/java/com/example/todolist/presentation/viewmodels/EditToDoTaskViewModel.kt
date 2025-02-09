package com.example.todolist.presentation.viewmodels

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.R
import com.example.todolist.domain.models.ToDoTask
import com.example.todolist.domain.usecases.SaveToDoTaskUseCase
import com.example.todolist.presentation.utils.ToDoTaskCreationEvent
import com.example.todolist.presentation.models.ToDoTaskCreationViewState
import com.example.todolist.common.utils.DateUtility
import com.example.todolist.presentation.utils.EventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditToDoTaskViewModel @Inject constructor (
    private val saveToDoTaskUseCase: SaveToDoTaskUseCase,
    private val dateUtility: DateUtility,
    private val resources: Resources
): ViewModel(), EventHandler<ToDoTaskCreationEvent> {

    private val _viewState = MutableLiveData(ToDoTaskCreationViewState(
        selectedDate = dateUtility.getCurrentDate())
    )
    val viewState: LiveData<ToDoTaskCreationViewState> = _viewState

    private var toDoTaskReceivedEventHandled = false

    init {
        prepareWheelTimePickerRows()
    }

    override fun obtainEvent(event: ToDoTaskCreationEvent) {
        when(event) {
            is ToDoTaskCreationEvent.TitleChanged -> toDoTaskTitleChanged(event.value)
            is ToDoTaskCreationEvent.DescriptionChanged -> toDoTaskDescriptionChanged(event.value)
            is ToDoTaskCreationEvent.SelectedDateChanged -> selectedDateChanged(event.value)
            is ToDoTaskCreationEvent.SelectedTimeChanged -> selectedTimeChanged(event.value)
            is ToDoTaskCreationEvent.ToDoTaskReceived -> {
                if(!toDoTaskReceivedEventHandled) toDoTaskReceived(event.value)
                toDoTaskReceivedEventHandled = true
            }
            is ToDoTaskCreationEvent.OnConfirmButtonClick -> saveToDoTask()
        }
    }

    private fun toDoTaskTitleChanged(value: String){
        _viewState.value = _viewState.value?.copy(toDoTaskTitle = value)
    }

    private fun toDoTaskDescriptionChanged(value: String){
        _viewState.value = _viewState.value?.copy(toDoTaskDescription = value)
    }

    private fun selectedDateChanged(value: Date) {
        _viewState.value = _viewState.value?.copy(selectedDate = value)
    }

    private fun selectedTimeChanged(value: Int) {
        _viewState.value = _viewState.value?.copy(selectedTime = value)
    }

    private fun toDoTaskReceived(value: ToDoTask) {
        _viewState.value = _viewState.value?.copy(
            toDoTaskId = value.id,
            toDoTaskTitle = value.title,
            toDoTaskDescription = value.description,
            selectedDate = value.toDoTaskBegin,
            selectedTime = getTimeHours(value.toDoTaskBegin)
        )
    }

    private fun prepareWheelTimePickerRows() {
        val listOfRows = mutableListOf<String>().also {
            for(i in 0 until DateUtility.HOURS_IN_DAY) {
                it.add(resources.getString(R.string.wheel_time_string, i, i+1))
            }
        }
        _viewState.value = _viewState.value?.copy(wheelTimePickerRows = listOfRows)
    }

    private fun saveToDoTask() {
        viewModelScope.launch {
            _viewState.postValue(_viewState.value?.copy(isSaveInProgress = true))

            val toDoTask = prepareToDoTask()

            val saveResult = saveToDoTaskUseCase.saveToDoTask(toDoTask)

            _viewState.postValue(_viewState.value?.copy(isToDoTaskSaved = saveResult))
        }
    }

    private fun prepareToDoTask(): ToDoTask {
        val id = _viewState.value?.toDoTaskId ?: 0
        val title = _viewState.value?.toDoTaskTitle ?: ""
        val description = _viewState.value?.toDoTaskDescription ?: ""
        val selectedDate = _viewState.value?.selectedDate ?: dateUtility.getCurrentDate()
        val selectedTime = _viewState.value?.selectedTime ?: 0

        val toDoTaskBeginTime = dateUtility.getSelectedDateWithSelectedTime(selectedDate, selectedTime)
        val toDoTaskEndTime = dateUtility.getSelectedDateWithSelectedTime(selectedDate, selectedTime + 1)

        val toDoTask = ToDoTask(
            id = id,
            title = title,
            description = description,
            toDoTaskBegin = toDoTaskBeginTime,
            toDoTaskEnd = toDoTaskEndTime
        )
        return toDoTask
    }

    fun getSelectedDate(year: Int, month: Int, day: Int): Date {
        return dateUtility.getSelectedDate(year, month, day)
    }

    fun getTimeHours(date: Date): Int {
        return dateUtility.getTimeHours(date)
    }
}