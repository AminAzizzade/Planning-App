package com.example.planningapp.room.project

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.planningapp.data.entity.project.ProjectTaskResource

interface ProjectTaskResourceDAO
{
    @Query("SELECT * FROM ProjectTaskResource")
    suspend fun getAllProjectTaskResources(): List<ProjectTaskResource>

    @Insert
    suspend fun insertProjectTaskResource(projectTaskResource: ProjectTaskResource)

    @Query("DELETE FROM ProjectTaskResource WHERE ProjectTaskResourceID = :projectTaskResourceId")
    suspend fun deleteProjectTaskResource(projectTaskResourceId: Int)

    @Update
    suspend fun updateProjectTaskResource(projectTaskResource: ProjectTaskResource)

}