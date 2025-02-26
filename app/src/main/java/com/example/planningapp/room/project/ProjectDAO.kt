package com.example.planningapp.room.project

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.planningapp.data.entity.project.Project

@Dao
interface ProjectDAO
{
    @Query("SELECT * FROM Project")
    suspend fun getAllProjects(): List<Project>

    @Insert
    suspend fun insertProject(project: Project)

    @Query("DELETE FROM Project WHERE ProjectID = :projectId")
    suspend fun deleteProject(projectId: Int)

}