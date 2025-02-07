package com.example.planningapp.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planningapp.service.DateExtractorService
import com.example.planningapp.view.datastructure.DailyTimeLineTasks
import com.example.planningapp.view.partialview.dps.DaySelector
import com.example.planningapp.view.partialview.dps.TaskPopupScreen
import com.example.planningapp.view.partialview.dps.TimelineItem
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel

/**
 * Görevleri temsil eden basit model
 */
data class Task(
    var taskName: String = "",
    val startTime: String = "",
    val endTime: String = ""
)

// Ayların son günlerini tutan dizi
val lastDayOfMonths = intArrayOf(31,28,31,30,31,30,31,31,30,31,30,31)

@Composable
fun DailyPlanningScreen(viewModel: DailyPlanningViewModel, date: String?)
{

    /**
     * Date verisinin dönüştürülmesi
     */
    var year  = 1
    var month = 1
    var calculatedDay  = 1

    if (date != null) {
        year = DateExtractorService.getYear(date)
        month = DateExtractorService.getMonth(date)
        calculatedDay = DateExtractorService.getDay(date)
    }

    val lastDayOfMonth = lastDayOfMonths[month - 1]

    /**
     * Verilerin kullanılacak değerlere aktarılması
     */
    var day by remember{ mutableIntStateOf(calculatedDay)}
    val dailyTimeLineTasks = remember {  DailyTimeLineTasks()}

    // -- day, month veya year her değiştiğinde çalışır
    LaunchedEffect(day, month, year) {
        viewModel.getOneDay(day, month, year)
    }
    val timeLine = viewModel.oneDay.observeAsState()

    // -- Timeline verilerini çekerek listeye ekle
    try {
        timeLine.value?.let { dayData ->
            dailyTimeLineTasks.clearAllTimeLine()
            dailyTimeLineTasks.addAllTimeLine(dayData.dailyTimeLine.timeLineList)
        }
    } catch (e: Exception) {
        Log.e("DailyPlanningScreen", "error: ${e.message}")
    } finally {
        Log.e("DailyPlanningScreen", "TimeLine count: ${dailyTimeLineTasks.timeLineList.size}")
    }

    val list = dailyTimeLineTasks.timeLineList


    /**
     * UI düzeni
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Spacer(modifier = Modifier.height(16.dp))

        // Gün seçici (5 günlük kaydırmalı liste)
        DaySelector(
            selectedDay = day,
            lastDayOfMonth = lastDayOfMonth,
            onDaySelected = {
                newDay -> day = newDay
            }
        )

        // Görevlerin (timeline) gösterildiği bölüm
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .background(Color.Black)
                .padding(16.dp)
                .size(500.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                itemsIndexed(list) { index, event ->
                    val previousEvent = list.getOrNull(index - 1)
                    TimelineItem(event, previousEvent)
                    {
                        Log.e("DailyPlanningScreen",event.taskID.toString())
                        viewModel.deleteTimeLineTask(event.taskID)
                    }
                }
            }
        }

        // Ekleme işlemi zamanı açılan popup
        Surface(
            modifier = Modifier.fillMaxHeight()
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                TaskPopupScreen(viewModel, day, month, year)
            }
        }
    }
}





