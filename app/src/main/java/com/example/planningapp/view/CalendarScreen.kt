package com.example.planningapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale




@Composable
fun CalendarScreen(onDayClick: (String) -> Unit) {
    val currentMonth = YearMonth.now()

    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek
    val today = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Month and Year Header
        Text(
            text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Days of Week Row
        val daysOfWeek = DayOfWeek.entries.map {
            it.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Days Grid
        val totalCells = (daysInMonth + firstDayOfMonth.value - 1) // Total slots in grid
        val rows = (totalCells / 7) + 1 // Calculate number of rows needed

        Column {
            var dayCounter = 1
            for (rowIndex in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (colIndex in 0 until 7) {
                        val day = if (rowIndex == 0 && colIndex < firstDayOfMonth.value - 1 ||
                            dayCounter > daysInMonth
                        ) null else dayCounter++

                        DayCell(
                            day = day,
                            isToday = today.dayOfMonth == day && today.month == currentMonth.month,
                            onClick = {
                                day?.let {
                                    val selectedDate = LocalDate.of(currentMonth.year, currentMonth.month, it)
                                    onDayClick(selectedDate.toString())
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DayCell(day: Int?, isToday: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .padding(4.dp)
            .background(
                color = when {
                    day == null -> Color.Transparent
                    isToday -> MaterialTheme.colorScheme.primary
                    else -> Color.White
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = day != null) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day?.toString() ?: "",
            fontSize = 16.sp,
            color = if (isToday) Color.White else Color.Black,
            textAlign = TextAlign.Center
        )
    }
}
