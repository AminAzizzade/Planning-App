package com.example.planningapp.data.repository.project

import com.example.planningapp.data.datasource.project.ProjectDataSource
import com.example.planningapp.data.entity.project.Project

class ProjectRepository(
    private val projectDataSource: ProjectDataSource
)
{
    suspend fun getAllProjects(): List<Project> = projectDataSource.getAllProjects()

    suspend fun insertProject(project: Project) = projectDataSource.insertProject(project)

    suspend fun deleteProject(projectId: Int) = projectDataSource.deleteProject(projectId)

}