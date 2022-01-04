package com.ajayspace.taskplanner.database

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tasks_list")
@Keep data class TodoItem(

     @PrimaryKey(autoGenerate = true)
     var id: Long =0L,

     @ColumnInfo(name="task_title")
     var title:String="TITLE",

     @ColumnInfo(name="task_desc")
     var desc:String="DESCRIPTION",

     @ColumnInfo(name="priority")
     var priority: String ,

     @ColumnInfo(name = "status")
     var status: String = "Incomplete",

     @ColumnInfo(name="is_completed")
     var isCompleted:Boolean=false
) :Serializable