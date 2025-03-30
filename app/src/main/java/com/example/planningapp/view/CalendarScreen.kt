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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planningapp.ui.theme.backgroundColor
import com.example.planningapp.ui.theme.mainColor
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.LaunchedEffect

@Composable
fun CalendarScreen(
    onDayClick: (LocalDate) -> Unit,
    selectedDate: LocalDate? = null
) {
    var currentMonth by remember { mutableStateOf(selectedDate?.let { YearMonth.of(it.year, it.month) } ?: YearMonth.now()) }

    LaunchedEffect(selectedDate) {
        selectedDate?.let {
            currentMonth = YearMonth.of(it.year, it.month)
        }
    }

    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Text(text = "<", fontSize = 24.sp, color = mainColor)
            }
            Text(
                text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            TextButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                Text(text = ">", fontSize = 24.sp, color = mainColor)
            }
        }

        val daysOfWeek = DayOfWeek.entries.map { it.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }
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

        val totalCells = daysInMonth + firstDayOfMonth.value - 1
        val rows = (totalCells / 7) + 1

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

                        val cellDate = if (day != null) LocalDate.of(currentMonth.year, currentMonth.month, day) else null
                        val isSelected = cellDate != null && selectedDate != null && cellDate == selectedDate

                        DayCell(
                            day = day,
                            isToday = isSelected, // Seçili gün için farklı bir stil uygulayabilirsiniz
                            onClick = {
                                day?.let {
                                    val date = LocalDate.of(currentMonth.year, currentMonth.month, it)
                                    onDayClick(date)
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
                    isToday -> mainColor
                    else -> backgroundColor
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = day != null) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day?.toString() ?: "",
            fontSize = 16.sp,
            color = if (isToday) backgroundColor else Color.Black,
            textAlign = TextAlign.Center
        )
    }
}
