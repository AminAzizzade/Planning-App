package com.example.planningapp.data.repository.project

import com.example.planningapp.data.datasource.project.ProjectTaskResourceDataSource
import com.example.planningapp.data.entity.project.ProjectTaskResource

class ProjectTaskResourceRepository(private val dataSource: ProjectTaskResourceDataSource)
{
    suspend fun getAllProjectTaskResources(): HashMap<Int, List<ProjectTaskResource>>
        = dataSource.getAllProjectTaskResources()

    suspend fun insertProjectTaskResource(projectTaskResource: ProjectTaskResource)
        = dataSource.insertProjectTaskResource(projectTaskResource)

    suspend fun deleteProjectTaskResource(projectTaskResourceId: Int)
        = dataSource.deleteProjectTaskResource(projectTaskResourceId)

    suspend fun updateProjectTaskResource(projectTaskResource: ProjectTaskResource)
        = dataSource.updateProjectTaskResource(projectTaskResource)
}