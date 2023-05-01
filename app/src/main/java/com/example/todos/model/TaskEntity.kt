package com.example.todos.model

import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val task: String,
    var isCompleted: Boolean = false

)
