package com.example.todos.ui.tasklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.R
import com.example.todos.databinding.FragmentTaskListBinding
import com.example.todos.model.TaskEntity
import com.example.todos.ui.bottomsheet.AddTaskFragment
import com.example.todos.ui.bottomsheet.UpdateTaskFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment(R.layout.fragment_task_list) {
    private lateinit var binding: FragmentTaskListBinding
    private val viewModel: TaskListViewModel by viewModels()
    private lateinit var adapter: TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObservers()

        adapter = TaskListAdapter {
            adapteronClick(it)
        }
    }

    private fun adapteronClick(task: TaskEntity) {
        val bundle = Bundle()

        bundle.putInt("key", task.id!!)

        bundle.putBoolean("keyStatus", task.isCompleted)

        val bottomSheetUpdate = UpdateTaskFragment {
            viewModel.showTask()
        }
        bottomSheetUpdate.show(parentFragmentManager, "updating data")

        bottomSheetUpdate.arguments = bundle
    }

    private fun initObservers() {
        viewModel.taskItems.observe(this) {
            adapter.submitList(it)
        }
        viewModel.eventSuccess.observe(this) {
        }
        viewModel.eventDeleteSuccess.observe(this) {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTaskListBinding.bind(view)

        initViews()

        initListeners()

        load()
    }

    private fun load() {
        viewModel.showTask()
    }

    private fun initListeners() {
        binding.addTask.setOnClickListener {
            val bottomSheetAdd = AddTaskFragment {
                viewModel.showTask()
            }
            bottomSheetAdd.show(parentFragmentManager, "Data is added")
        }
    }

    private fun initViews() {
        val layoutManager = LinearLayoutManager(activity)
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter
    }
}
