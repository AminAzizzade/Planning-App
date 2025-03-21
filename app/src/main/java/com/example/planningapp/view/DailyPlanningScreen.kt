package com.example.planningapp.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.service.DateExtractorService
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.textColor
import com.example.planningapp.view.partialview._dps.DaySelector
import com.example.planningapp.view.partialview._dps.TaskPopupScreen
import com.example.planningapp.view.partialview._dps.TimelineItem
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
fun DailyPlanningScreen(
    viewModel: DailyPlanningViewModel,
    date: String?,
    onTaskClick: (Int) -> Unit,
    navController: NavHostController
)
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


    /**
     * Fetch monthly data
     */

    LaunchedEffect(month) {
        viewModel.getMonthDays(month, year)
    }
    val monthDays = viewModel.monthDays.observeAsState()


    val timelineTasks by remember(day, month, year, monthDays.value) {
        derivedStateOf {
            val dayOfYear = (day) + 100 * month + 10000 * year
            monthDays.value?.get(dayOfYear)?.dailyTimeLine?.timeLineList ?: emptyList()
        }
    }

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

        IconListLazyRow(navController = navController)

        Spacer(modifier = Modifier.height(16.dp))

        TimeLineView(viewModel, timelineTasks, onTaskClick)

        TaskPopupScreen(viewModel, day, month, year)

        Spacer(modifier = Modifier.height(16.dp))

        // Gün seçici (5 günlük kaydırmalı liste)
        DaySelector(
            selectedDay = day,
            lastDayOfMonth = lastDayOfMonth,
            onDaySelected = {
                    newDay -> day = newDay
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun TimeLineView(
    viewModel: DailyPlanningViewModel,
    list: List<TimeLineTask>,
    onTaskClick: (Int) -> Unit)
{
    Surface(
        modifier = Modifier
            .fillMaxHeight(0.8f)
            .background(Color.Black)
            //.padding(16.dp)
            .size(500.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        )
        {
            items(list) { event ->
                TimelineItem(
                    viewModel,
                    onTaskClick = onTaskClick,
                    event = event,
                )
            }
        }
    }
}


@Composable
fun IconListLazyRow(navController: NavHostController) {
    // İkon verilerini tutan veri sınıfı
    data class IconItem(
        val imageVector: ImageVector,
        val contentDescription: String,
        val defaultColor: Color,
        val selectedColor: Color,
        val destination: String
    )

    // İkonları içeren liste
    val icons = listOf(
        IconItem(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Check Circle",
            defaultColor = textColor,
            selectedColor = textColor,
            destination = "project"
        ),
        IconItem(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Date Range",
            defaultColor = mainColor,
            selectedColor = mainColor,
            destination = "calendar"
        ),
        IconItem(
            imageVector = Icons.Default.Build,
            contentDescription = "Build",
            defaultColor = textColor,
            selectedColor = textColor,
            destination = "calendar"
        )
    )

    // Seçili ikonun index değerini tutan state
    var selectedIndex by remember { mutableStateOf(1) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(icons) { index, iconItem ->
            IconButton(
                onClick = {
                    Log.d("Navigation", "Selected Index: $index")
                    navController.navigate(icons[index].destination)
                },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { selectedIndex = index },
                    imageVector = iconItem.imageVector,
                    contentDescription = iconItem.contentDescription,
                    tint = if (selectedIndex == index) mainColor else textColor
                )
            }

        }
    }
}
