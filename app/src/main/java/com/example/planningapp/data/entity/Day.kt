package com.example.planningapp.data.entity

import com.example.planningapp.view.datastructure.DailyTimeLineTasks
import java.time.LocalDate

data class Day(
    val day: Int,
    val month: Int,
    val year: Int,
    val dailyTimeLine: DailyTimeLineTasks = DailyTimeLineTasks()
)