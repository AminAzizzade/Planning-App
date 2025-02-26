package com.example.planningapp.room.project

import androidx.room.Insert
import androidx.room.Query
import com.example.planningapp.data.entity.project.ProjectHistory

interface ProjectHistoryDAO
{
    @Query("SELECT * FROM ProjectHistory")
    suspend fun getAllProjectHistory(): List<ProjectHistory>

    @Insert
    suspend fun insertProjectHistory(projectHistory: ProjectHistory)

    @Query("DELETE FROM ProjectHistory WHERE ProjectHistoryID = :projectHistoryId")
    suspend fun deleteProjectHistory(projectHistoryId: Int)

}