package com.example.planningapp.data.datasource.project

import com.example.planningapp.data.entity.project.Project
import com.example.planningapp.room.project.ProjectDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectDataSource(private val dao: ProjectDAO)
{

    suspend fun getAllProjects(): List<Project> = withContext(Dispatchers.IO)
    {
        return@withContext dao.getAllProjects()
    }

    suspend fun insertProject(project: Project) = withContext(Dispatchers.IO)
    {
        dao.insertProject(project)
    }

    suspend fun deleteProject(projectId: Int) = withContext(Dispatchers.IO)
    {
        dao.deleteProject(projectId)
    }

}