package com.example.planningapp.room.project

import androidx.room.Dao
import androidx.room.Query
import com.example.planningapp.data.entity.project.ProjectDescription

@Dao
interface ProjectDescriptionDAO
{
    @Query("SELECT * FROM ProjectDescription")
    suspend fun getAllProjectDescription(): List<ProjectDescription>

    @Query("SELECT * FROM ProjectDescription WHERE ProjectID = :projectId")
    suspend fun getProjectDescription(projectId: Int): ProjectDescription

    @Query("INSERT INTO ProjectDescription (ProjectID, Description) VALUES (:projectId, :description)")
    suspend fun insertProjectDescription(projectId: Int, description: String)

    @Query("DELETE FROM ProjectDescription WHERE ProjectDescriptionID = :projectDescriptionId")
    suspend fun deleteProjectDescription(projectDescriptionId: Int)

    @Query("UPDATE ProjectDescription SET Description = :description WHERE ProjectDescriptionID = :projectDescriptionId")
    suspend fun updateProjectDescription(projectDescriptionId: Int, description: String)
}