package com.example.planningapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.planningapp.data.entity.CheckBoxMission

@Dao
interface CheckBoxMissionDAO {
    @Insert
    suspend fun insertContent(mission: CheckBoxMission)

    @Query("DELETE FROM CheckBoxMission WHERE contentId = :foreignKey")
    suspend fun removeContent(foreignKey: Int)
}