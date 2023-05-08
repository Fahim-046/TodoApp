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

    private val _eventSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val eventSuccess: LiveData<Boolean>
        get() = _eventSuccess

    private val _eventDeleteSuccess: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>(false)
    }

    val eventDeleteSuccess: LiveData<Boolean?>
        get() = _eventDeleteSuccess

    fun showTask() = viewModelScope.launch {
        try {
            val tasks = repository.getAllTask()
            Log.e("TaskListViewModel show task", "show task ${tasks.count()}")
            _taskItems.value = tasks
        } catch (e: Exception) {
            _taskItems.postValue(listOf())

            Log.d("TaskListViewModel error", "somethig wrong happened")

            e.printStackTrace()
        }
    }

    // Updating data
    private val _todo: MutableLiveData<TaskEntity> by lazy {
        MutableLiveData<TaskEntity>()
    }

    val todo: LiveData<TaskEntity?>
        get() = _todo

    fun getTask(id: Int) = viewModelScope.launch {
        try {
            val task = repository.specificTask(id)

            _todo.value =task
        } catch (e: Exception) {
            Log.d("TaskListViewModel message-error", "Didn't get the task data")
        }
    }

    fun updateTask(id: Int, task: String, status: Boolean) = viewModelScope.launch {
        try {
            val response = repository.updateTask(TaskEntity(id, task, status))

            _eventSuccess.value = true
        } catch (e: Exception) {
            _eventSuccess.value = false

            Log.d("TaskListViewModel message-error", "${e.message}")
        }
    }

    fun isValid(
        task: String
    ): Boolean {
        if (task.isBlank()) {
            return false
        }
        return true
    }

    fun saveTask(
        task: String
    ) = viewModelScope.launch {
        if (!isValid(task)) {
            return@launch
        }

        try {
            repository.saveTask(TaskEntity(null, task, false))

            _eventSuccess.value = true
        } catch (e: Exception) {
            Log.d("TaskListViewModel error", "Task is not added")
        }
    }

    fun deleteTask(id: Int) = viewModelScope.launch {
        try {
            repository.deleteTask(id)
            _eventDeleteSuccess.value=true
        } catch (e: Exception) {
            Log.d("TaskListViewModel errorFound", "Data has been deleted")
        }
    }
}
