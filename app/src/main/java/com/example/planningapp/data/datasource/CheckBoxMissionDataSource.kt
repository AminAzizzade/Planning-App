package com.example.planningapp.data.datasource

import android.util.Log
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TaskLabel
import com.example.planningapp.room.CheckBoxMissionDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CheckBoxMissionDataSource(private val dao: CheckBoxMissionDAO)
{

    suspend fun getMissions(contentId: Int): List<CheckBoxMission> = withContext(Dispatchers.IO)
    {
        Log.e("TaskContentScreen", "istenilen contentId: $contentId")
        return@withContext dao.getMissions(contentId)
    }

    suspend fun insertMissions(mission: List<CheckBoxMission>) = withContext(Dispatchers.IO)
    {
        dao.insertMissions(mission)
    }

    suspend fun updateMissions(missions: List<CheckBoxMission>) = withContext(Dispatchers.IO)
    {
        for (mission in missions)
        {
            dao.updateMission(mission.missionId, mission.missionName, mission.check)
        }
    }

    suspend fun removeMissions(missions: List<CheckBoxMission>) = withContext(Dispatchers.IO)
    {
        for (mission in missions)
        {
            dao.removeMissions(mission.missionId)
        }
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

    suspend fun getAllMissions(): HashMap<Int, List<CheckBoxMission>> = withContext(Dispatchers.IO)
    {
        val list = dao.getAllMissions()
        val map = list.groupBy { it.contentId } as HashMap<Int, List<CheckBoxMission>>

        return@withContext map
    }
}