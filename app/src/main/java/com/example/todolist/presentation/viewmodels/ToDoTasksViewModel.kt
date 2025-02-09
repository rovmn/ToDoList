package com.example.todolist.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.common.utils.PendingResult
import com.example.todolist.common.utils.Result
import com.example.todolist.domain.models.ToDoTask
import com.example.todolist.domain.usecases.DeleteToDoTaskUseCase
import com.example.todolist.domain.usecases.GetToDoTasksByDateUseCase
import com.example.todolist.common.utils.DateUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ToDoTasksViewModel @Inject constructor(
    private val getToDoTasksByDateUseCase: GetToDoTasksByDateUseCase,
    private val deleteToDoTaskUseCase: DeleteToDoTaskUseCase,
    private val dateUtility: DateUtility
) : ViewModel() {

    private val _selectedDate = MutableLiveData<Date>(dateUtility.getCurrentDate())
    val selectedDate: LiveData<Date> = _selectedDate

    private val _toDoTasks = MutableLiveData<Result<List<ToDoTask>>>(PendingResult())
    val toDoTasks: LiveData<Result<List<ToDoTask>>> = _toDoTasks

    fun setSelectedDate(year: Int, month: Int, day: Int) {
        _selectedDate.postValue(dateUtility.getSelectedDate(year, month, day))
    }

    fun getToDoTasks(selectedDate: Date? = null) {
        viewModelScope.launch {
            _toDoTasks.postValue(getToDoTasksByDateUseCase.getToDoTasksByDate(
                selectedDate ?:
                _selectedDate.value ?:
                dateUtility.getCurrentDate())
            )
        }
    }

    fun deleteToDoTask(toDoTaskId: Long) {
        viewModelScope.launch {
            deleteToDoTaskUseCase.deleteToDoTask(toDoTaskId)
            getToDoTasks()
        }
    }
}