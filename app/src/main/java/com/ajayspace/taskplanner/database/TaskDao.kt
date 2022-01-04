package com.ajayspace.taskplanner.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT* FROM tasks_list WHERE status= :status")
    suspend fun getTaskBy(status: String): TodoItem

    @Query("SELECT* FROM tasks_list WHERE priority= :priority")
    suspend fun getTasksByPriority(priority: String): List<TodoItem>?

    @Query("SELECT* FROM tasks_list")
    suspend fun allTasks(): List<TodoItem>

    @Query("SELECT* FROM tasks_list WHERE status != 'incomplete'")
    suspend fun getAllIncompleteTasks(): List<TodoItem>?

    @Query("SELECT* FROM tasks_list WHERE status = :status")
    suspend fun getTasksWithStatus(status:String): List<TodoItem>?

    @Insert
    suspend fun insert(night: TodoItem)

    @Query("DELETE FROM tasks_list")
    suspend fun clear()

    @Query("SELECT* FROM tasks_list WHERE id=:id")
    suspend fun getTaskById(id: Long): TodoItem

    @Update
    suspend fun updateTask(todo: TodoItem)

}