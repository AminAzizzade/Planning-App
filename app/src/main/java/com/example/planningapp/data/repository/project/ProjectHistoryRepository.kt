package com.example.planningapp.data.repository.project

import com.example.planningapp.data.datasource.project.ProjectHistoryDataSource
import com.example.planningapp.data.entity.project.ProjectHistory

class ProjectHistoryRepository(
    private val projectHistoryDataSource: ProjectHistoryDataSource
)
{
    suspend fun getProjectHistory(projectId: Int): List<ProjectHistory> =
        projectHistoryDataSource.getProjectHistory(projectId)

    suspend fun insertProjectHistory(projectHistory: ProjectHistory) =
        projectHistoryDataSource.insertProjectHistory(projectHistory)

    suspend fun deleteProjectHistory(projectHistoryId: Int) =
        projectHistoryDataSource.deleteProjectHistory(projectHistoryId)
}