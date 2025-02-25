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

    @Query("UPDATE TaskContent SET Note = :missionNote, Label = :label WHERE ContentID = :contentId")
    suspend fun updateContent(contentId: Int, label: String, missionNote: String)

    @Query("DELETE FROM TaskContent WHERE ContentID = :contentId")
    suspend fun removeContent(contentId: Int)

    @Query("SELECT * FROM TaskContent")
    suspend fun getAllContents() : List<TaskContent>

}