package com.example.planningapp.data.repository

import com.example.planningapp.data.datasource.CheckBoxMissionDataSource
import com.example.planningapp.data.entity.CheckBoxMission

class CheckBoxMissionRepository(private val dataSource: CheckBoxMissionDataSource)
{

    suspend fun getMissions(contentId: Int): List<CheckBoxMission>
    = dataSource.getMissions(contentId)

    suspend fun insertMissions(mission: List<CheckBoxMission>)
    = dataSource.insertMissions(mission)

    suspend fun updateMissions(missions: List<CheckBoxMission>)
    = dataSource.updateMissions(missions)

    suspend fun insertMission(mission: CheckBoxMission)
    = dataSource.insertMission(mission)

    suspend fun updateMission(mission: CheckBoxMission)
            = dataSource.updateMission(mission)

    suspend fun deleteMission(mission: CheckBoxMission)
    = dataSource.deleteMission(mission)

    suspend fun getAllMissions()
    = dataSource.getAllMissions()
}