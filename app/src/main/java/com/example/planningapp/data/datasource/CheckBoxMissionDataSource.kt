package com.example.planningapp.data.datasource

import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.room.CheckBoxMissionDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckBoxMissionDataSource(private val dao: CheckBoxMissionDAO)
{

    suspend fun getMissions(contentId: Int): List<CheckBoxMission> = withContext(Dispatchers.IO)
    {
        return@withContext dao.getMissions(contentId)
    }

    suspend fun insertMission(mission: CheckBoxMission) = withContext(Dispatchers.IO)
    {
        dao.insertMission(mission)
    }

    suspend fun updateMission(mission: CheckBoxMission) = withContext(Dispatchers.IO)
    {
        dao.updateMission(mission.missionId, mission.missionName, mission.check)
    }

    suspend fun deleteMission(mission: CheckBoxMission) = withContext(Dispatchers.IO)
    {
        dao.deleteMission(mission.missionId)
    }

    suspend fun checkMission(missionId: Int) = withContext(Dispatchers.IO) {
        dao.checkMission(missionId)
    }

    suspend fun uncheckMission(missionId: Int) = withContext(Dispatchers.IO) {
        dao.uncheckMission(missionId)
    }

    suspend fun getAllMissions(): HashMap<Int, List<CheckBoxMission>> = withContext(Dispatchers.IO)
    {
        val list = dao.getAllMissions()
        val map = list.groupBy { it.contentId } as HashMap<Int, List<CheckBoxMission>>

        return@withContext map
    }
}