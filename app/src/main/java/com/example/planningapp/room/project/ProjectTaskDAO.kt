package com.example.planningapp.room.project

import androidx.room.Insert
import androidx.room.Query
import com.example.planningapp.data.entity.project.ProjectTask

interface ProjectTaskDAO
{
    @Query("SELECT * FROM ProjectTask")
    suspend fun getAllProjectTasks(): List<ProjectTask>

    @Insert
    suspend fun insertProjectTask(projectTask: ProjectTask)

    @Query("DELETE FROM ProjectTask WHERE ProjectTaskID = :projectTaskId")
    suspend fun deleteProjectTask(projectTaskId: Int)
}