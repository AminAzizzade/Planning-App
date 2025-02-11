package com.example.planningapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.planningapp.data.entity.TaskContent

@Dao
interface TaskContentDAO {

    @Insert
    suspend fun insertContent(content: TaskContent)

    @Query("DELETE FROM TaskContent WHERE taskId = :foreignKey")
    suspend fun removeContent(foreignKey: Int)

}