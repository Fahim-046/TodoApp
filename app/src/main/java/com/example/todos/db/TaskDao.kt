package com.example.todos.db

import androidx.room.*
import com.example.todos.model.TaskEntity

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(task: TaskEntity):Long

    @Query("SELECT * FROM tasks")
    suspend fun fetchTasks(): List<TaskEntity>

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTasks(id: Int)

    @Update
    suspend fun update(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskBYId(id: Int): TaskEntity?


}
