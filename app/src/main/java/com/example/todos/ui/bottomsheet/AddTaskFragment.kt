package com.example.todos.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.todos.databinding.FragmentAddTaskBinding
import com.example.todos.ui.tasklist.TaskListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment(
    private val onSuccess: () -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddTaskBinding

    private val viewModel: TaskListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        viewModel.eventSuccess.observe(this) {
            if (it == true) {
                dismiss()

                onSuccess()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(layoutInflater, container, false)

        binding.addBtn.setOnClickListener {
            val task:String = binding.taskName.text.toString()
            viewModel.saveTask(task)
        }

        return binding.root
    }
}
