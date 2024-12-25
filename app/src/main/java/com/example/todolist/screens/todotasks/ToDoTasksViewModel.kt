package com.example.todolist.screens.todotasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.model.PendingResult
import com.example.todolist.model.Result
import com.example.todolist.model.todotask.ToDoTaskRepository
import com.example.todolist.model.todotask.entities.ToDoTask
import com.example.todolist.utils.DateUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ToDoTasksViewModel @Inject constructor(
    private val toDoTaskRepository: ToDoTaskRepository
) : ViewModel() {

    private val _selectedDate = MutableLiveData<Date>(DateUtility.getCurrentDate())
    val selectedDate: LiveData<Date> = _selectedDate

    private val _toDoTasks = MutableLiveData<Result<List<ToDoTask>>>(PendingResult())
    val toDoTasks: LiveData<Result<List<ToDoTask>>> = _toDoTasks

    fun setSelectedDate(year: Int, month: Int, day: Int) {
        _selectedDate.postValue(DateUtility.getDate(year, month, day))
    }

    fun getToDoTasks(selectedDate: Date? = null) {
        viewModelScope.launch {
            _toDoTasks.postValue(toDoTaskRepository.getToDoTasks(
                selectedDate ?:
                _selectedDate.value ?:
                DateUtility.getCurrentDate())
            )
        }
    }

    fun deleteToDoTask(toDoTaskId: Long) {
        viewModelScope.launch {
            toDoTaskRepository.deleteToDoTask(toDoTaskId)
            getToDoTasks()
        }
    }
}