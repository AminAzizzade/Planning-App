package com.example.planningapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.planningapp.data.entity.TimeLineTask

@Dao
interface TimeLineTaskDAO
{
    @Insert
    suspend fun insertTask(task: TimeLineTask)

    @Query("SELECT * FROM TimeLineTask")
    suspend fun uploadAllTimeLimeTasks(): List<TimeLineTask>

    @Query("SELECT * FROM TimeLineTask WHERE Day = :day AND Month = :month AND Year = :year")
    suspend fun uploadTimeLimeTasksByDate(day: Int, month: Int, year: Int): List<TimeLineTask>


    @Query("SELECT * FROM TimeLineTask WHERE Month = :month AND Year = :year")
    suspend fun uploadTimeLimeTasksByMonth(month: Int, year: Int): List<TimeLineTask>

    @Query("DELETE  FROM TimeLineTask WHERE Name = :name")
    suspend fun deleteTimeLimeTasksByMonth(name: String)

    @Query("DELETE  FROM TimeLineTask WHERE TaskID = :id")
    suspend fun deleteTimeLimeTasksByID(id: Int)
}