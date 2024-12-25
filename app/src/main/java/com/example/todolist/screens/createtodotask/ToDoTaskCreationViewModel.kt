package com.example.todolist.screens.createtodotask

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.todotask.ToDoTaskRepository
import com.example.todolist.model.todotask.entities.ToDoTask
import com.example.todolist.screens.createtodotask.model.ToDoTaskCreationEvent
import com.example.todolist.screens.createtodotask.model.ToDoTaskCreationViewState
import com.example.todolist.utils.DateUtility
import com.example.todolist.utils.EventHandler
import com.example.todolist.utils.getTimeHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ToDoTaskCreationViewModel @Inject constructor (
    private val toDoTaskRepository: ToDoTaskRepository
): ViewModel(), EventHandler<ToDoTaskCreationEvent> {

    private val _viewState = MutableLiveData(ToDoTaskCreationViewState())
    val viewState: LiveData<ToDoTaskCreationViewState> = _viewState

    private var toDoTaskReceivedEventHandled = false

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
            is ToDoTaskCreationEvent.OnConfirmButtonClick -> checkToDoTask()
        }
    }

    private fun toDoTaskTitleChanged(value: String){
        _viewState.postValue(_viewState.value?.copy(toDoTaskTitle = value))
    }

    private fun toDoTaskDescriptionChanged(value: String){
        _viewState.postValue(_viewState.value?.copy(toDoTaskDescription = value))
    }

    private fun selectedDateChanged(value: Date) {
        _viewState.postValue(_viewState.value?.copy(selectedDate = value))
    }

    private fun selectedTimeChanged(value: Int) {
        _viewState.postValue(_viewState.value?.copy(selectedTime = value))
    }

    private fun toDoTaskReceived(value: ToDoTask) {
        _viewState.postValue(_viewState.value?.copy(
            toDoTaskId = value.id,
            toDoTaskTitle = value.title,
            toDoTaskDescription = value.description,
            selectedDate = value.toDoTaskBegin,
            selectedTime = value.toDoTaskBegin.getTimeHours()
        ))
    }

    private fun checkToDoTask() {
        viewModelScope.launch {
            _viewState.postValue(_viewState.value?.copy(isSaveInProgress = true))

            val title = _viewState.value?.toDoTaskTitle ?: ""
            val description = _viewState.value?.toDoTaskDescription ?: ""
            val selectedDate = _viewState.value?.selectedDate ?: DateUtility.getCurrentDate()
            val selectedTime = _viewState.value?.selectedTime ?: 0

            val toDoTaskBeginTime = DateUtility.setDate(selectedDate, selectedTime)
            val toDoTaskEndTime = DateUtility.setDate(selectedDate, selectedTime + 1)

            val toDoTask = ToDoTask(
                id = _viewState.value?.toDoTaskId ?: 0,
                title = title,
                description = description,
                toDoTaskBegin = toDoTaskBeginTime,
                toDoTaskEnd = toDoTaskEndTime
            )

            val saveResult = if(_viewState.value?.toDoTaskId != null) {
                toDoTaskRepository.updateToDoTask(toDoTask)
            } else {
                toDoTaskRepository.createToDoTask(toDoTask)
            }
            _viewState.postValue(_viewState.value?.copy(isSaveInProgress = false ,isToDoTaskSaved = saveResult))
        }
    }
}