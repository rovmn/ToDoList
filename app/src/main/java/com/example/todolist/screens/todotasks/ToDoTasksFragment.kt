package com.example.todolist.screens.todotasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.FragmentToDoTasksBinding
import com.example.todolist.databinding.PartResultBinding
import com.example.todolist.model.EmptyResult
import com.example.todolist.model.ErrorResult
import com.example.todolist.model.PendingResult
import com.example.todolist.model.SuccessResult
import com.example.todolist.model.todotask.entities.ToDoTask
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class ToDoTasksFragment : Fragment() {

    private val viewModel: ToDoTasksViewModel by viewModels<ToDoTasksViewModel>()

    private lateinit var binding: FragmentToDoTasksBinding
    private lateinit var resultBinding: PartResultBinding
    private lateinit var adapter: ToDoTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoTasksBinding.inflate(inflater, container, false)
        resultBinding = PartResultBinding.bind(binding.root)

        viewModel.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            binding.calendarView.date = selectedDate.time
            viewModel.getToDoTasks(selectedDate)
        }

        binding.calendarView.setOnDateChangeListener{ view, y, m, d ->
            viewModel.setSelectedDate(y, m, d)
        }

        binding.addButton.setOnClickListener {
            val direction = ToDoTasksFragmentDirections
                .actionToDoTasksFragmentToToDoTaskCreationFragment(null)
            findNavController().navigate(direction)
        }

        resultBinding.tryAgainButton.setOnClickListener {
            viewModel.getToDoTasks()
        }

        adapter = ToDoTaskAdapter(requireContext(), object: ItemClickListener {
            override fun onToDoTaskDetails(toDoTask: ToDoTask) {
                val direction = ToDoTasksFragmentDirections
                    .actionToDoTasksFragmentToToDoTaskCreationFragment(toDoTask)
                findNavController().navigate(direction)
            }

            override fun onToDoTaskDelete(toDoTask: ToDoTask) {
                viewModel.deleteToDoTask(toDoTask.id)
            }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.toDoTasksRecyclerView.layoutManager = layoutManager
        binding.toDoTasksRecyclerView.adapter = adapter

        viewModel.toDoTasks.observe(viewLifecycleOwner) { result ->
            when(result) {
                is PendingResult -> {
                    resultBinding.progressBar.visibility = View.VISIBLE
                    resultBinding.errorContainer.visibility = View.GONE
                    binding.toDoTasksRecyclerView.visibility = View.GONE
                }
                is ErrorResult -> {
                    resultBinding.progressBar.visibility = View.GONE
                    resultBinding.errorContainer.visibility = View.VISIBLE
                    binding.toDoTasksRecyclerView.visibility = View.GONE
                }
                is SuccessResult -> {
                    adapter.setRecyclerviewDataset(result.data)

                    resultBinding.progressBar.visibility = View.GONE
                    resultBinding.errorContainer.visibility = View.GONE
                    binding.toDoTasksRecyclerView.visibility = View.VISIBLE
                }
                is EmptyResult -> {
                    adapter.setRecyclerviewDataset(null)

                    resultBinding.progressBar.visibility = View.GONE
                    resultBinding.errorContainer.visibility = View.GONE
                    binding.toDoTasksRecyclerView.visibility = View.VISIBLE
                }
            }
        }

        return binding.root
    }
}