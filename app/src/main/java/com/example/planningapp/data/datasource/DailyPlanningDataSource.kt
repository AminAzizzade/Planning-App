package com.example.planningapp.data.datasource

import com.example.planningapp.data.entity.Day
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.room.TimeLineTaskDAO
import com.example.planningapp.view.datastructure.DailyTimeLineTasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyPlanningDataSource(private var dao: TimeLineTaskDAO)
{

    suspend fun insertDatabase(day: Int, month: Int, year: Int, taskName : String, startTime: Int, endTime: Int) = withContext(Dispatchers.IO)
    {
        val newTimeLineTask = TimeLineTask(
            taskID = 0, // autoGenerate is true, so you can set it to 0
            day = day,
            month = month,
            year = year,
            taskName = taskName,
            startTime = startTime,
            endTime = endTime
        )

        dao.insertTask(newTimeLineTask)
    }

    suspend fun testInsertCode() = withContext(Dispatchers.IO)
    {
        val newTimeLineTask = TimeLineTask(
            taskID = 0, // autoGenerate is true, so you can set it to 0
            day = 5,
            month = 2,
            year = 2025,
            taskName = "Amin",
            startTime = 140,
            endTime = 160
        )

        dao.insertTask(newTimeLineTask)
    }

    suspend fun testDeleteCode() = withContext(Dispatchers.IO)
    {
        dao.deleteTimeLimeTasksByMonth("Amin")
    }

    suspend fun uploadAllTimeLineTasks(): List<TimeLineTask> = withContext(Dispatchers.IO) {
        //insertDatabase()
        return@withContext dao.uploadAllTimeLimeTasks()
    }

    suspend fun uploadTimeLineTasksForDay(day: Int, month: Int, year: Int): Day = withContext(Dispatchers.IO)
    {
        val timeLineTasks = dao.uploadTimeLimeTasksByDate(day, month, year)
        val dailyTimeLineTasks = DailyTimeLineTasks()
        dailyTimeLineTasks.addAllTimeLine(timeLineTasks)

        val returningDay = Day(day, month, year, dailyTimeLineTasks)

        return@withContext returningDay
    }

    suspend fun uploadTimeLineTasksForMonth(month: Int, year: Int): HashMap<Int, Day> = withContext(Dispatchers.IO)
    {
        val timeLineTasks = dao.uploadTimeLimeTasksByMonth(month, year)
        val returningMonth = HashMap<Int, Day>()
        for (timeLine: TimeLineTask in timeLineTasks)
        {
            val dailyTimeLineTasks = DailyTimeLineTasks()
            dailyTimeLineTasks.addAllTimeLine(timeLineTasks)

            val mapDay = Day(timeLine.day, timeLine.month, timeLine.year, dailyTimeLineTasks)
            returningMonth[
                convertDateInteger(timeLine.day, timeLine.month, timeLine.year)
            ] = mapDay
        }

        return@withContext returningMonth
    }


}

fun convertDateInteger(day: Int, month: Int, year: Int): Int {
    val date = year*10000 + month*100 + day
    return date
}