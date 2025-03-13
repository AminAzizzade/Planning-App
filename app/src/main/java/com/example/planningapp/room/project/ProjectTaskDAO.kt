package com.example.planningapp.room.project

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.planningapp.data.entity.project.ProjectTask

@Dao
interface ProjectTaskDAO
{
    @Query("SELECT * FROM ProjectTask")
    suspend fun getAllProjectTasks(): List<ProjectTask>

    @Query("SELECT * FROM ProjectTask WHERE ProjectID = :projectId")
    suspend fun getProjectTasks(projectId: Int): List<ProjectTask>

    @Insert
    suspend fun insertProjectTask(projectTask: ProjectTask)

    @Query("DELETE FROM ProjectTask WHERE ProjectTaskID = :projectTaskId")
    suspend fun deleteProjectTask(projectTaskId: Int)
}