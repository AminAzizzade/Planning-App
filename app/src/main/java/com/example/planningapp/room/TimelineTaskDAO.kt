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
    suspend fun uploadAllTimeLineTasks(): List<TimeLineTask>

    @Query("SELECT * FROM TimeLineTask WHERE Day = :day AND Month = :month AND Year = :year")
    suspend fun uploadTimeLineTasksByDate(day: Int, month: Int, year: Int): List<TimeLineTask>


    @Query("SELECT * FROM TimeLineTask WHERE Month = :month AND Year = :year")
    suspend fun uploadTimeLineTasksByMonth(month: Int, year: Int): List<TimeLineTask>

    @Query("DELETE  FROM TimeLineTask WHERE Name = :name")
    suspend fun deleteTimeLineTasksByMonth(name: String)

    @Query("DELETE  FROM TimeLineTask WHERE TaskID = :id")
    suspend fun deleteTimeLineTasksByID(id: Int)

    @Query("UPDATE TimeLineTask SET Name = :taskName, StartTime = :startTime, EndTime = :endTime WHERE TaskID = :id")
    suspend fun updateTimeLineTasksByID(id: Int, taskName: String, startTime: Int, endTime: Int)
}