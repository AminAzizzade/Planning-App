package com.example.planningapp.data.datasource.project

import com.example.planningapp.data.entity.project.ProjectDescription
import com.example.planningapp.room.project.ProjectDescriptionDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectDescriptionDataSource (private val dao: ProjectDescriptionDAO)
{

    suspend fun getAllProjectDescription(): List<ProjectDescription> = withContext(Dispatchers.IO)
    {
        return@withContext dao.getAllProjectDescription()
    }

    suspend fun getProjectDescription(projectId: Int): ProjectDescription = withContext(Dispatchers.IO)
    {
        return@withContext dao.getProjectDescription(projectId)
    }

    suspend fun insertProjectDescription(projectId: Int, description: String) = withContext(Dispatchers.IO)
    {
        dao.insertProjectDescription(projectId, description)
    }

    suspend fun deleteProjectDescription(projectDescriptionId: Int) = withContext(Dispatchers.IO)
    {
        dao.deleteProjectDescription(projectDescriptionId)

    }

    suspend fun updateProjectDescription(projectDescriptionId: Int, description: String) = withContext(Dispatchers.IO)
    {
        dao.updateProjectDescription(projectDescriptionId, description)
    }

}