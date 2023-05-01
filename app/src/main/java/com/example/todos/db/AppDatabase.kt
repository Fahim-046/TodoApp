package com.example.todos.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomMasterTable
import com.example.todos.model.TaskEntity

@Database(entities = [TaskEntity::class],
    version = 1 )
abstract class AppDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object{
        operator fun invoke(context: Context) = buildDatabase(context)
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "TaskDatabase.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}