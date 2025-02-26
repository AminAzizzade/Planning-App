package com.example.planningapp.data.datasource

import android.util.Log
import com.example.planningapp.data.entity.Day
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.room.TimeLineTaskDAO
import com.example.planningapp.view.datastructure.DailyTimeLineTasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyPlanningDataSource(private var dao: TimeLineTaskDAO)
{

    suspend fun uploadTimeLineTasksForMonth2(month: Int, year: Int): HashMap<Int, Day> = withContext(Dispatchers.IO) {
        // Veritabanından gelen timeline görevlerini çek
        val timeLineTasks = dao.uploadTimeLineTasksByMonth(month, year)

        // Görevleri gün bazında grupla
        val groupedTasks: Map<Int, List<TimeLineTask>> = timeLineTasks.groupBy { it.day }
        val returningMonth = hashMapOf<Int, Day>()

        groupedTasks.forEach { (day, tasksForDay) ->
            // Her gün için DailyTimeLineTasks oluştur ve o güne ait görevleri ekle
            val dailyTimeLineTasks = DailyTimeLineTasks().apply { addAllTimeLine(tasksForDay) }

            // Yardımcı fonksiyon veya mapping işlemiyle Day nesnesi oluştur
            val mapDay = Day(day, month, year, dailyTimeLineTasks)

            // Unique key oluşturmak için extension veya yardımcı fonksiyon kullan
            returningMonth[convertDateInteger(day, month, year)] = mapDay
            Log.d("DailyPlanningScreen1", "Created mapDay: $mapDay")
        }

        returningMonth
    }


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

    suspend fun deleteTimeLineTaskById(id:Int) = withContext(Dispatchers.IO)
    {
        dao.deleteTimeLineTasksByID(id)
    }


    suspend fun updateTimeLineTaskById(id: Int, taskName: String, startTime: Int, endTime: Int) = withContext(Dispatchers.IO)
    {
        dao.updateTimeLineTasksByID(id, taskName, startTime, endTime)
    }

    suspend fun uploadAllTimeLineTasks(): List<TimeLineTask> = withContext(Dispatchers.IO) {
        //insertDatabase()
        return@withContext dao.uploadAllTimeLineTasks()
    }

    suspend fun uploadTimeLineTasksForDay(day: Int, month: Int, year: Int): Day = withContext(Dispatchers.IO)
    {
        val timeLineTasks = dao.uploadTimeLineTasksByDate(day, month, year)
        val dailyTimeLineTasks = DailyTimeLineTasks()
        dailyTimeLineTasks.addAllTimeLine(timeLineTasks)

        val returningDay = Day(day, month, year, dailyTimeLineTasks)

        return@withContext returningDay
    }

    // Yardımcı fonksiyon: Tarihi tekil bir Int değere çevirir
    fun convertDateInteger(day: Int, month: Int, year: Int): Int = year * 10000 + month * 100 + day

}
