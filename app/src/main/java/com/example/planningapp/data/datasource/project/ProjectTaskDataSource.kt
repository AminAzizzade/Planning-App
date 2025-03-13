package com.example.planningapp.data.datasource.project

import com.example.planningapp.data.entity.project.ProjectTask
import com.example.planningapp.room.project.ProjectTaskDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectTaskDataSource (private val projectTaskDAO: ProjectTaskDAO)
{

    suspend fun getAllProjectTasks(): HashMap<Int, List<ProjectTask>> = withContext(Dispatchers.IO) {
        val list = projectTaskDAO.getAllProjectTasks()
        val map = list.groupBy { it.projectId } as HashMap<Int, List<ProjectTask>>
        return@withContext map
    }

    suspend fun getProjectTasks(projectId: Int): List<ProjectTask> = withContext(Dispatchers.IO) {
        return@withContext projectTaskDAO.getProjectTasks(projectId)
    }

    suspend fun insertProjectTask(projectTask: ProjectTask) = withContext(Dispatchers.IO) {
        projectTaskDAO.insertProjectTask(projectTask)
    }

    suspend fun deleteProjectTask(projectTaskId: Int) = withContext(Dispatchers.IO) {
        projectTaskDAO.deleteProjectTask(projectTaskId)
    }

}