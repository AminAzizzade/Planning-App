package com.example.planningapp.data.datasource.project

import com.example.planningapp.data.entity.project.ProjectTaskResource
import com.example.planningapp.room.project.ProjectTaskResourceDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectTaskResourceDataSource(
    private val projectTaskResourceDAO: ProjectTaskResourceDAO
)
{
    suspend fun getAllProjectTaskResources(): HashMap<Int, List<ProjectTaskResource>> = withContext(Dispatchers.IO) {
        val list =  projectTaskResourceDAO.getAllProjectTaskResources()
        val map = list.groupBy { it.projectTaskId } as HashMap<Int, List<ProjectTaskResource>>
        return@withContext map
    }

    suspend fun insertProjectTaskResource(projectTaskResource: ProjectTaskResource) = withContext(Dispatchers.IO) {
        projectTaskResourceDAO.insertProjectTaskResource(projectTaskResource)
    }

    suspend fun deleteProjectTaskResource(projectTaskResourceId: Int) = withContext(Dispatchers.IO) {
        projectTaskResourceDAO.deleteProjectTaskResource(projectTaskResourceId)
    }

    suspend fun updateProjectTaskResource(projectTaskResource: ProjectTaskResource) = withContext(Dispatchers.IO) {
        projectTaskResourceDAO.updateProjectTaskResource(projectTaskResource)
    }

}