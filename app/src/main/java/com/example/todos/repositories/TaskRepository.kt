package com.example.todos.repositories

import com.example.todos.db.AppDatabase
import com.example.todos.model.TaskEntity
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository @Inject constructor(private val db: AppDatabase) {

    suspend fun saveTask(task: TaskEntity) {
        return withContext(Dispatchers.IO) {
            db.taskDao().add(task)
        }
    }

    suspend fun getAllTask(): List<TaskEntity> {
        return withContext(Dispatchers.IO) {
            db.taskDao().fetchTasks()
        }
    }

    suspend fun specificTask(id: Int): TaskEntity? {
        return withContext(Dispatchers.IO) {
            db.taskDao().getTaskBYId(id)
        }
    }

    suspend fun updateTask(task:TaskEntity){
        return withContext(Dispatchers.IO){
            db.taskDao().update(task)
        }
    }
}
