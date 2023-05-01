package com.example.todos.ui.tasklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.databinding.ListItemBinding
import com.example.todos.model.TaskEntity

class TaskListAdapter(
    private val onCLick: (TaskEntity) -> Unit
) : ListAdapter<TaskEntity, TaskListAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, onCLick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        return holder.bind(task)
    }

    class ViewHolder(
        private val binding: ListItemBinding,
        private val onClick: (TaskEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskEntity) {
            binding.taskTv.text = item.task
            binding.completeBox.isChecked = item.isCompleted

            binding.root.setOnClickListener {
                onClick(item)
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onCLick: (TaskEntity) -> Unit
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onCLick)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TaskEntity>() {
            override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
