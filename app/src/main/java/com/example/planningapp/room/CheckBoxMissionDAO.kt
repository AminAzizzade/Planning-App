package com.example.planningapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.planningapp.data.entity.CheckBoxMission

@Dao
interface CheckBoxMissionDAO {

    @Query("SELECT * FROM CheckBoxMission WHERE ContentID = :contentId")
    suspend fun getMissions(contentId: Int): List<CheckBoxMission>

    @Insert
    suspend fun insertMissions(missions: List<CheckBoxMission>)

    @Query("""UPDATE CheckBoxMission SET MissionName = :missionName, "Check" = :check WHERE MissionID = :missionId""")
    suspend fun updateMission(missionId: Int, missionName: String, check: Boolean)

    @Query("DELETE FROM CheckBoxMission WHERE MissionID = :missionId")
    suspend fun removeMissions(missionId: Int)

    @Insert
    suspend fun insertMission(mission: CheckBoxMission)

    @Query("DELETE FROM CheckBoxMission WHERE MissionID = :missionId")
    suspend fun deleteMission(missionId: Int)

}