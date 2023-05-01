package com.example.todos.ui.bottomsheet

import android.text.Editable
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
class AddTaskViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {
    private val _eventSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val eventSuccess: LiveData<Boolean>
        get() = _eventSuccess

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
}
