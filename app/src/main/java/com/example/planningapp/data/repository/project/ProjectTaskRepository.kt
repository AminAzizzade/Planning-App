package com.example.planningapp.data.repository.project

import com.example.planningapp.data.datasource.project.ProjectTaskDataSource
import com.example.planningapp.data.entity.project.ProjectTask

class ProjectTaskRepository(private val projectTaskDataSource: ProjectTaskDataSource)
{
    suspend fun getProjectTasks(projectId: Int): List<ProjectTask> =
        projectTaskDataSource.getProjectTasks(projectId)

    suspend fun insertProjectTask(projectTask: ProjectTask) =
        projectTaskDataSource.insertProjectTask(projectTask)

    suspend fun deleteProjectTask(projectTaskId: Int) =
        projectTaskDataSource.deleteProjectTask(projectTaskId)
}