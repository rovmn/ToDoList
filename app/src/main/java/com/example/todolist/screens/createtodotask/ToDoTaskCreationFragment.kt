package com.example.todolist.screens.createtodotask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.commandiron.wheel_picker_compose.core.WheelTextPicker
import com.example.todolist.databinding.FragmentToDoTaskCreationBinding
import com.example.todolist.model.ErrorResult
import com.example.todolist.model.SuccessResult
import com.example.todolist.model.todotask.entities.ToDoTask
import com.example.todolist.screens.createtodotask.model.ToDoTaskCreationEvent
import com.example.todolist.screens.createtodotask.model.ToDoTaskCreationViewState
import com.example.todolist.utils.Const
import com.example.todolist.utils.DateUtility
import com.example.todolist.utils.getTimeHours
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class ToDoTaskCreationFragment : Fragment() {

    private lateinit var binding: FragmentToDoTaskCreationBinding

    private val viewModel: ToDoTaskCreationViewModel by viewModels<ToDoTaskCreationViewModel>()

    private val timeWheelTextPickerItems: List<String> by lazy {
        return@lazy mutableListOf<String>().apply {
            for(i in 0 until Const.HOURS_IN_A_DAY) {
                this.add(getString(R.string.wheel_time_string, i, i+1))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentToDoTaskCreationBinding.inflate(inflater, container, false)

        val toDoTask = ToDoTaskCreationFragmentArgs.fromBundle(requireArguments()).toDoTask

        toDoTask?.let {
            viewModel.obtainEvent(ToDoTaskCreationEvent.ToDoTaskReceived(it))
        }

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            binding.calendarView.date = viewState.selectedDate.time

            if(viewState.isSaveInProgress) {
                binding.calendarView.setOnDateChangeListener{ view, y, m, d ->
                    binding.calendarView.date = viewState.selectedDate.time
                }
            } else {
                binding.calendarView.setOnDateChangeListener{ view, y, m, d ->
                    viewModel.obtainEvent(ToDoTaskCreationEvent
                        .SelectedDateChanged(DateUtility.getDate(y, m, d)))
                }
            }
        }

        binding.composeView.setContent {
            val viewState = viewModel.viewState.observeAsState(ToDoTaskCreationViewState())

            ToDoTaskCreationView(
                viewState = viewState,
                toDoTask = toDoTask,
                timeWheelTextPickerItems = timeWheelTextPickerItems,
                onScrollFinished = {
                    viewModel.obtainEvent(ToDoTaskCreationEvent.SelectedTimeChanged(it))
                    null
                },
                onTitleValueChange = {
                    viewModel.obtainEvent(ToDoTaskCreationEvent.TitleChanged(it))
                },
                onDescriptionValueChange = {
                    viewModel.obtainEvent(ToDoTaskCreationEvent.DescriptionChanged(it))
                },
                onCancelButtonClick = {
                    findNavController().popBackStack()
                },
                onConfirmButtonClick = {
                    viewModel.obtainEvent(ToDoTaskCreationEvent.OnConfirmButtonClick)
                },
                onEmptyFields = {
                    Toast.makeText(requireContext(), R.string.fill_in_the_fields, Toast.LENGTH_LONG).show()
                },
                onSuccessfulSave = {
                    Toast.makeText(requireContext(), R.string.successful_save, Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                },
                onFailedSave = {
                    Toast.makeText(requireContext(), R.string.error_try_again, Toast.LENGTH_SHORT).show()
                }
            )
        }

        return binding.root
    }
}

@Composable
fun ToDoTaskCreationView(
    viewState: State<ToDoTaskCreationViewState>,
    toDoTask: ToDoTask?,
    timeWheelTextPickerItems: List<String>,
    onScrollFinished: (Int) -> Int?,
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onCancelButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onEmptyFields: () -> Unit,
    onSuccessfulSave: () -> Unit,
    onFailedSave: () -> Unit,

){
    Column{
        if(viewState.value.isSaveInProgress) {
            Box(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .height(128.dp)
                    .background(color = colorResource(R.color.primary_background), shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.wheel_time_string, viewState.value.selectedTime, viewState.value.selectedTime+1),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black.copy(alpha = 0.2F)
                )
            }
        } else {
            TimeWheelTextPicker(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .background(colorResource(R.color.primary_background), shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                startIndex = toDoTask?.toDoTaskBegin?.getTimeHours() ?: viewState.value.selectedTime,
                timeWheelTextPickerItems = timeWheelTextPickerItems,
                onScrollFinished = onScrollFinished,
            )
        }
        ToDoTaskTextField(
            value = viewState.value.toDoTaskTitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            placeholder = stringResource(R.string.task_title),
            enabled = !viewState.value.isSaveInProgress,
            onValueChange = onTitleValueChange,
        )
        ToDoTaskTextField(
            value = viewState.value.toDoTaskDescription,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            placeholder = stringResource(R.string.task_description_text),
            enabled = !viewState.value.isSaveInProgress,
            onValueChange = onDescriptionValueChange,
        )
        if(viewState.value.isSaveInProgress) {
            Box(
                modifier = Modifier
                    .padding(top = 3.dp, bottom = 3.dp)
                    .background(color = colorResource(R.color.primary_background), shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    strokeWidth = 3.dp,
                    color = colorResource(R.color.border_color)
                )
            }
        } else {
            Row {
                CancelButton(
                    modifier = Modifier
                        .padding(end = 3.dp)
                        .fillMaxWidth(0.5f),
                    onClick = onCancelButtonClick,
                )
                ConfirmButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        if(viewState.value.toDoTaskTitle == "" ||
                        viewState.value.toDoTaskDescription == ""){
                            onEmptyFields.invoke()
                        } else {
                            onConfirmButtonClick.invoke()
                        }
                    },
                )
            }
        }
    }
    when(viewState.value.isToDoTaskSaved) {
        is SuccessResult -> {
            onSuccessfulSave.invoke()
        }
        is ErrorResult -> {
            onFailedSave.invoke()
        }
        else -> {}
    }
}

@Composable
private fun ToDoTaskTextField(
    value: String,
    placeholder: String,
    enabled: Boolean,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        placeholder = {
            Text(placeholder)
        },
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            disabledIndicatorColor = (Color.White),
            unfocusedContainerColor = (colorResource(R.color.primary_background)),
            errorIndicatorColor = (colorResource(R.color.primary_background)),
            focusedIndicatorColor = (Color.White),
            unfocusedIndicatorColor = (Color.White),
            disabledContainerColor = (colorResource(R.color.primary_background))
        )
    )
}

@Composable
private fun CancelButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.cancel_button_background),
            contentColor = colorResource(R.color.black)
        )
    ) {
        Text(stringResource(R.string.cancel))
    }
}

@Composable
private fun ConfirmButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.confirm_button_background),
            contentColor = colorResource(R.color.black)
        )
    ) {
        Text(stringResource(R.string.confirm))
    }
}

@Composable
private fun TimeWheelTextPicker(
    modifier: Modifier,
    startIndex: Int,
    timeWheelTextPickerItems: List<String>,
    onScrollFinished: (Int) -> Int?,
) {
    WheelTextPicker(
        modifier = modifier,
        startIndex = startIndex,
        rowCount = 3,
        texts = timeWheelTextPickerItems,
        selectorProperties = WheelPickerDefaults.selectorProperties(
            color = colorResource(R.color.secondary_background),
            border = BorderStroke(1.dp, colorResource(R.color.border_color)),
        ),
        onScrollFinished = onScrollFinished
    )
}