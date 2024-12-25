package com.example.todolist.screens.todotasks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.ItemToDoTaskBinding
import com.example.todolist.model.todotask.entities.ToDoTask
import com.example.todolist.utils.Const
import com.example.todolist.utils.getTimeHours

interface ItemClickListener{
    fun onToDoTaskDetails(toDoTask: ToDoTask)

    fun onToDoTaskDelete(toDoTask: ToDoTask)
}

private class TasksDiffCallback(
    private val oldList: List<ToDoTask?>,
    private val newList: List<ToDoTask?>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

class ToDoTaskAdapter(
    private val context: Context,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<ToDoTaskAdapter.ToDoTaskViewHolder>(), View.OnClickListener {

    var toDoTasks = listOf<ToDoTask?>()
        set(newValue) {
            val diffCallback = TasksDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    fun setRecyclerviewDataset(newToDoTasks: List<ToDoTask>?) {
        val tempToDoTasks = arrayOfNulls<ToDoTask>(Const.HOURS_IN_A_DAY).toMutableList()
        newToDoTasks?.forEach{ toDoTask ->
            val position = toDoTask.toDoTaskBegin.getTimeHours()
            tempToDoTasks[position] = toDoTask
        }
        toDoTasks = tempToDoTasks
    }

    override fun onClick(view: View) {
        val toDoTask = view.tag as ToDoTask?
        toDoTask?.let {
            when(view.id){
                R.id.deleteTaskImageView -> {
                    itemClickListener.onToDoTaskDelete(toDoTask)
                }
                else -> {
                    itemClickListener.onToDoTaskDetails(toDoTask)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoTaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemToDoTaskBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.deleteTaskImageView.setOnClickListener(this)

        return ToDoTaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoTaskViewHolder, position: Int) {

        holder.binding.toDoTaskBegin.text = context.getString(R.string.task_time_begin, position)
        holder.binding.toDoTaskEnd.text = context.getString(R.string.task_time_end, position+1)

        if(toDoTasks[position] == null) {
            holder.itemView.tag = null
            holder.binding.deleteTaskImageView.tag = null

            holder.binding.toDoTaskItemTitleText.text = ""
            holder.binding.toDoTaskItemDescriptionText.text = ""

            holder.binding.deleteTaskImageView.visibility = View.GONE
        } else {
            toDoTasks[position]?.let{
                holder.itemView.tag = it
                holder.binding.deleteTaskImageView.tag = it

                holder.binding.toDoTaskItemTitleText.text = it.title
                holder.binding.toDoTaskItemDescriptionText.text = it.description

                holder.binding.deleteTaskImageView.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int = toDoTasks.size

    class ToDoTaskViewHolder(
        val binding: ItemToDoTaskBinding
    ): RecyclerView.ViewHolder(binding.root)
}