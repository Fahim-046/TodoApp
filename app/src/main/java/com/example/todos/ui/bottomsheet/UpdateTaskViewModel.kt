package com.example.todos.ui.bottomsheet

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
class UpdateTaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {
    private val _todo: MutableLiveData<TaskEntity> by lazy {
        MutableLiveData<TaskEntity>()
    }

    val todo: LiveData<TaskEntity?>
        get() = _todo

    private val _eventSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val eventSuccess: LiveData<Boolean>
        get() = _eventSuccess

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
}
