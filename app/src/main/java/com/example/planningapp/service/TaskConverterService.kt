package com.example.planningapp.service

import com.example.planningapp.data.entity.TaskStatus
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.view.Task

object TaskConverterService {
    private val timeService = TimeConverterService

    fun convertToTimeLineTask(task: Task, taskID: Int, day: Int, month: Int, year: Int): TimeLineTask {
        return TimeLineTask(
            taskID = taskID,
            day = day,
            month = month,
            year = year,
            taskName = task.taskName,
            startTime = timeService.convert(task.startTime), // "HH:MM" → Integer (dakika cinsinden)
            endTime = timeService.convert(task.endTime), // "HH:MM" → Integer (dakika cinsinden)
            status = TaskStatus.IS_UNSPECIFIED
        )
    }

    fun convertToTask(timeLineTask: TimeLineTask): Task {
        return Task(
            taskName = timeLineTask.taskName,
            startTime = timeService.convert(timeLineTask.startTime), // Integer (dakika) → "HH:MM"
            endTime = timeService.convert(timeLineTask.endTime) // Integer (dakika) → "HH:MM"
        )
    }
}
