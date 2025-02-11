package com.example.planningapp.data.datasource

import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TaskLabel
import com.example.planningapp.room.CheckBoxMissionDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckBoxMissionDataSource(private val dao: CheckBoxMissionDAO)
{
    suspend fun testInsert() = withContext(Dispatchers.IO)
    {
        val foreignKey = 1
        val mission = CheckBoxMission(
            missionId = 0,
            contentId = foreignKey,
            check = true,
            missionName = "test"
        )

        dao.insertContent(mission)
    }
    suspend fun testRemove() = withContext(Dispatchers.IO)
    {
        val foreignKey = 1
        val mission = CheckBoxMission(
            missionId = 0,
            contentId = foreignKey,
            check = true,
            missionName = "test"
        )

        dao.removeContent(foreignKey)
    }
}