package com.example.planningapp.data.repository.project

import com.example.planningapp.data.datasource.project.ProjectDescriptionDataSource

class ProjectDescriptionRepository(private val dataSource: ProjectDescriptionDataSource)
{

    suspend fun getAllProjectDescription() = dataSource.getAllProjectDescription()

    suspend fun getProjectDescription(projectId: Int) = dataSource.getProjectDescription(projectId)

    suspend fun insertProjectDescription(projectId: Int, description: String) = dataSource.insertProjectDescription(projectId, description)

    suspend fun deleteProjectDescription(projectDescriptionId: Int) = dataSource.deleteProjectDescription(projectDescriptionId)

    suspend fun updateProjectDescription(projectDescriptionId: Int, description: String) = dataSource.updateProjectDescription(projectDescriptionId, description)

}