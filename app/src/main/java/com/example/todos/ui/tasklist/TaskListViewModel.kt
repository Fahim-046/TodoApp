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
            _taskItems.value = tasks
        } catch (e: Exception) {
            _taskItems.postValue(listOf())

            Log.d("error", "somethig wrong happened")
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

            _todo.postValue(task)
        } catch (e: Exception) {
            Log.d("message-error", "Didn't get the task data")
        }
    }

    fun updateTask(id: Int, task: String, status: Boolean) = viewModelScope.launch {
        try {
            val response = repository.updateTask(TaskEntity(id, task, status))

            _eventSuccess.postValue(true)
        } catch (e: Exception) {
            _eventSuccess.postValue(false)

            Log.d("message-error", "${e.message}")
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

            _eventSuccess.postValue(true)
        } catch (e: Exception) {
            Log.d("error", "Task is not added")
        }
    }

    fun deleteTask(id: Int) = viewModelScope.launch {
        try {
            repository.deleteTask(id)
            _eventDeleteSuccess.postValue(true)
        } catch (e: Exception) {
            Log.d("errorFound", "Data has been deleted")
        }
    }
}
