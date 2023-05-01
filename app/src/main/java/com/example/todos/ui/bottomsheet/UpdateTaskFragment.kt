package com.example.todos.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.todos.databinding.FragmentUpdateTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateTaskFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentUpdateTaskBinding

    private val viewModel: UpdateTaskViewModel by viewModels()

    private var taskId: Int = 0
    private var taskStatus:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskId = it.getInt("key")
            taskStatus = it.getBoolean("keyStatus")
        }
        initObservers()
    }

    private fun initObservers() {
        viewModel.eventSuccess.observe(this){
            if(it==true)dismiss()
        }

        viewModel.todo.observe(this){
            binding.taskName.setText(it!!.task)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateTaskBinding.inflate(layoutInflater, container, false)

        initListeners()

        getTask()

        return binding.root
    }

    private fun getTask() {
        viewModel.getTask(taskId)
    }

    private fun initListeners() {
        binding.saveBtn.setOnClickListener {
            viewModel.updateTask(
                taskId,
                binding.taskName.text.toString(),
                taskStatus
            )
        }
    }
}
