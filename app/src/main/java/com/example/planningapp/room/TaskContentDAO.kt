package com.example.planningapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.planningapp.data.entity.TaskContent

@Dao
interface TaskContentDAO {

    @Query("SELECT * FROM TaskContent WHERE TaskID = :taskId")
    suspend fun getContents(taskId: Int): TaskContent

    @Insert
    suspend fun insertContent(content: TaskContent)

    @Update
    suspend fun updateContent1(content: TaskContent)

    @Query("DELETE FROM TaskContent WHERE ContentID = :contentId")
    suspend fun removeContent(contentId: Int)

    @Query("SELECT * FROM TaskContent")
    suspend fun getAllContents() : List<TaskContent>

}