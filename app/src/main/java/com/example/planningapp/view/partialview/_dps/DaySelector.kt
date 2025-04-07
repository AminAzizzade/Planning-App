package com.example.planningapp.view.partialview._dps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.planningapp.ui.theme.backgroundColor
import com.example.planningapp.ui.theme.mainColor

@Composable
fun DaySelector(
    lastDayOfMonth: Int,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    val days = List(lastDayOfMonth) { it + 1 }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val itemWidth = 50.dp
    val spacing = 8.dp
    val itemWidthPx = with(density) { itemWidth.toPx() }
    val spacingPx = with(density) { spacing.toPx() }

    LaunchedEffect(selectedDay) {
        val index = selectedDay - 1
        val targetOffset =
            (index * (itemWidthPx + spacingPx) + itemWidthPx / 2 - screenWidthPx / 2).toInt().coerceAtLeast(0)
        scrollState.animateScrollTo(targetOffset)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .background(mainColor)
            .padding(vertical = 8.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement
            .spacedBy(spacing)
    ) {
        days.forEach { day ->
            DayItem(day, selectedDay, onDaySelected)
        }
    }
}

@Composable
fun DayItem(day: Int, selectedDay: Int, onDaySelected: (Int) -> Unit) {
    val isSelected = day == selectedDay
    val backgroundColor = if (isSelected) backgroundColor else Color.Transparent
    val textColor = if (isSelected) mainColor else Color.White
    val borderWidth = if (isSelected) 2.dp else 0.dp
    val borderColor = if (isSelected) mainColor else Color.Transparent

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onDaySelected(day) }
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = day.toString(),
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}
