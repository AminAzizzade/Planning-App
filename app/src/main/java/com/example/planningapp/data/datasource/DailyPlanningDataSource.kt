package com.example.planningapp.data.datasource

import com.example.planningapp.data.entity.Day
import com.example.planningapp.data.entity.TaskStatus
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.room.TimeLineTaskDAO
import com.example.planningapp.view.datastructure.DailyTimeLineTasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyPlanningDataSource(private val dao: TimeLineTaskDAO) {

    suspend fun uploadTimeLineTasksForMonth(month: Int, year: Int): HashMap<Int, Day> = withContext(Dispatchers.IO) {
        val timeLineTasks = dao.uploadTimeLineTasksByMonth(month, year)
        val groupedTasks = timeLineTasks.groupBy { it.day }
        val returningMonth = hashMapOf<Int, Day>()

        groupedTasks.forEach { (day, tasksForDay) ->
            val dailyTimeLineTasks = DailyTimeLineTasks().apply { addAllTimeLine(tasksForDay) }
            val mapDay = Day(day, month, year, dailyTimeLineTasks)
            returningMonth[convertDateInteger(day, month, year)] = mapDay
        }
        returningMonth
    }

    suspend fun insertDatabase(day: Int, month: Int, year: Int, taskName: String, startTime: Int, endTime: Int) = withContext(Dispatchers.IO) {
        val newTimeLineTask = TimeLineTask(
            taskID = 0,
            day = day,
            month = month,
            year = year,
            taskName = taskName,
            startTime = startTime,
            endTime = endTime,
            status = TaskStatus.IS_UNSPECIFIED
        )
        dao.insertTask(newTimeLineTask)
    }

    suspend fun deleteTimeLineTaskById(id: Int) = withContext(Dispatchers.IO) {
        dao.deleteTimeLineTasksByID(id)
    }

    suspend fun updateTimeLineTaskById(id: Int, taskName: String, startTime: Int, endTime: Int) = withContext(Dispatchers.IO) {
        dao.updateTimeLineTasksByID(id, taskName, startTime, endTime)
    }

    private fun convertDateInteger(day: Int, month: Int, year: Int): Int = year * 10000 + month * 100 + day

    suspend fun updateTaskStatusById( id: Int, status: TaskStatus) {
        dao.updateTaskStatusById(id, status)
    }
}
