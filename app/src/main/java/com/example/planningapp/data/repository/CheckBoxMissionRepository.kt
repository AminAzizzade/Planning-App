package com.example.planningapp.data.repository

import com.example.planningapp.data.datasource.CheckBoxMissionDataSource
import com.example.planningapp.data.entity.CheckBoxMission

class CheckBoxMissionRepository(private val dataSource: CheckBoxMissionDataSource)
{

    suspend fun getMissions(contentId: Int): List<CheckBoxMission>
    = dataSource.getMissions(contentId)

    suspend fun insertMission(mission: CheckBoxMission)
    = dataSource.insertMission(mission)

    suspend fun deleteMission(mission: CheckBoxMission)
    = dataSource.deleteMission(mission)

    suspend fun getAllMissions()
    = dataSource.getAllMissions()

    suspend fun checkMission(missionId: Int)
    = dataSource.checkMission(missionId)

    suspend fun uncheckMission(missionId: Int)
    = dataSource.uncheckMission(missionId)

}