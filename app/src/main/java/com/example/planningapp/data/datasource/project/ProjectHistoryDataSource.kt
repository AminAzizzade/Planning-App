package com.example.planningapp.data.datasource.project

import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.project.ProjectHistory
import com.example.planningapp.room.project.ProjectHistoryDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectHistoryDataSource(private val projectHistoryDAO: ProjectHistoryDAO)
{
    suspend fun getAllProjectHistory(): HashMap<Int, List<ProjectHistory>> = withContext(Dispatchers.IO) {
        val list = projectHistoryDAO.getAllProjectHistory()
        val map = list.groupBy { it.projectId } as HashMap<Int, List<ProjectHistory>>
        return@withContext map
    }

    suspend fun getProjectHistory(projectId: Int): List<ProjectHistory> = withContext(Dispatchers.IO) {
        return@withContext projectHistoryDAO.getProjectHistory(projectId)
    }

    suspend fun insertProjectHistory(projectHistory: ProjectHistory) = withContext(Dispatchers.IO) {
        projectHistoryDAO.insertProjectHistory(projectHistory)
    }

    suspend fun deleteProjectHistory(projectHistoryId: Int) = withContext(Dispatchers.IO) {
        projectHistoryDAO.deleteProjectHistory(projectHistoryId)
    }
}