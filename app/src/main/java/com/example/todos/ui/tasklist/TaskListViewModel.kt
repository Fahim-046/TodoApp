package com.example.todos.ui.tasklist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todos.model.TaskEntity
import com.example.todos.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private val _taskItems: MutableLiveData<List<TaskEntity>> by lazy {
        MutableLiveData<List<TaskEntity>>()
    }

    val taskItems: LiveData<List<TaskEntity>?>
        get() = _taskItems

    fun showTask() = viewModelScope.launch {
        try {
            val tasks = repository.getAllTask()
            _taskItems.value = tasks
        } catch (e: Exception) {
            _taskItems.postValue(listOf())

            Log.d("error", "somethig wrong happened")
        }
    }
}
