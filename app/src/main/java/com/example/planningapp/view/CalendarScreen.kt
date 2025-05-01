package com.example.planningapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planningapp.ui.theme.backgroundColor
import com.example.planningapp.ui.theme.mainColor
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(
    onDayClick: (LocalDate) -> Unit,
    selectedDate: LocalDate? = null
) {
    var currentMonth by remember {
        mutableStateOf(
            selectedDate?.let { YearMonth.of(it.year, it.month) } ?: YearMonth.now()
        )
    }
    var showMonthMenu by remember { mutableStateOf(false) }
    var showYearMenu by remember { mutableStateOf(false) }
    val monthScrollState = rememberScrollState()
    val yearScrollState = rememberScrollState()
    val density = LocalDensity.current

    LaunchedEffect(selectedDate) {
        selectedDate?.let {
            currentMonth = YearMonth.of(it.year, it.month)
        }
    }

    // Menü açıldığında seçili öğeye göre ortalama işlemi
    LaunchedEffect(showMonthMenu) {
        if (showMonthMenu) {
            with(density) {
                val itemHeightPx = 48.dp.toPx()
                val containerHeightPx = 200.dp.toPx()
                val index = Month.entries.indexOf(currentMonth.month)
                val scrollPosition =
                    (index * itemHeightPx - containerHeightPx / 2 + itemHeightPx / 2)
                        .toInt()
                        .coerceAtLeast(0)
                monthScrollState.animateScrollTo(scrollPosition)
            }
        }
    }
    LaunchedEffect(showYearMenu) {
        if (showYearMenu) {
            with(density) {
                val itemHeightPx = 48.dp.toPx()
                val containerHeightPx = 200.dp.toPx()
                val startYear = currentMonth.year - 10
                val index = currentMonth.year - startYear
                val scrollPosition =
                    (index * itemHeightPx - containerHeightPx / 2 + itemHeightPx / 2)
                        .toInt()
                        .coerceAtLeast(0)
                yearScrollState.animateScrollTo(scrollPosition)
            }
        }
    }

    Column(
        modifier = Modifier
            //.fillMaxSize()
            .background(backgroundColor)
    ) {
        CalendarHeader(
            currentMonth = currentMonth,
            onPreviousClick = { currentMonth = currentMonth.minusMonths(1) },
            onNextClick = { currentMonth = currentMonth.plusMonths(1) },
            onMonthClick = { showMonthMenu = true },
            onYearClick = { showYearMenu = true },
            monthDropdown = {
                MonthDropdown(
                    currentMonth = currentMonth,
                    showMenu = showMonthMenu,
                    onDismiss = { showMonthMenu = false },
                    onMonthSelected = { selectedMonth ->
                        currentMonth = YearMonth.of(currentMonth.year, selectedMonth)
                        showMonthMenu = false
                    },
                    scrollState = monthScrollState
                )
            },
            yearDropdown = {
                YearDropdown(
                    currentMonth = currentMonth,
                    showMenu = showYearMenu,
                    onDismiss = { showYearMenu = false },
                    onYearSelected = { selectedYear ->
                        currentMonth = YearMonth.of(selectedYear, currentMonth.month)
                        showYearMenu = false
                    },
                    scrollState = yearScrollState
                )
            }
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.05F))

        DaysOfWeekRow()

        CalendarGrid(
            currentMonth = currentMonth,
            selectedDate = selectedDate,
            onDayClick = onDayClick
        )
    }
}

@Composable
fun NavigationButton(
    imageVector: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    size: Int = 40
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(mainColor, shape = RoundedCornerShape(size.dp / 2))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = Color.White
        )
    }
}

@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onMonthClick: () -> Unit,
    onYearClick: () -> Unit,
    monthDropdown: @Composable () -> Unit,
    yearDropdown: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationButton(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Önceki ay",
            onClick = onPreviousClick
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Ay seçimi alanı
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onMonthClick() }
                        .padding(8.dp)
                ) {
                    Text(
                        text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = mainColor
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Ay seçimi",
                        tint = mainColor
                    )
                }
                monthDropdown()
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Yıl seçimi alanı
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { onYearClick() }
                        .padding(8.dp)
                ) {
                    Text(
                        text = currentMonth.year.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = mainColor
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Yıl seçimi",
                        tint = mainColor
                    )
                }
                yearDropdown()
            }
        }
        NavigationButton(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Sonraki ay",
            onClick = onNextClick
        )
    }
}

@Composable
fun MonthDropdown(
    currentMonth: YearMonth,
    showMenu: Boolean,
    onDismiss: () -> Unit,
    onMonthSelected: (Month) -> Unit,
    scrollState: androidx.compose.foundation.ScrollState
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .shadow(8.dp, shape = RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(4.dp)
            ) {
                Month.entries.forEach { month ->
                    val isSelected = month == currentMonth.month
                    DropdownMenuItem(
                        modifier = if (isSelected) Modifier.background(
                            mainColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp) // Border-radius artırıldı
                        ) else Modifier,
                        text = {
                            Text(
                                text = month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) mainColor else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(8.dp)
                            )
                        },
                        onClick = { onMonthSelected(month) }
                    )
                }
            }
        }
    }
}

@Composable
fun YearDropdown(
    currentMonth: YearMonth,
    showMenu: Boolean,
    onDismiss: () -> Unit,
    onYearSelected: (Int) -> Unit,
    scrollState: androidx.compose.foundation.ScrollState
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .shadow(8.dp, shape = RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(4.dp)
            ) {
                val startYear = currentMonth.year - 10
                val endYear = currentMonth.year + 10
                (startYear..endYear).forEach { year ->
                    val isSelected = year == currentMonth.year
                    DropdownMenuItem(
                        modifier = if (isSelected) Modifier.background(
                            mainColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        ) else Modifier,
                        text = {
                            Text(
                                text = year.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) mainColor else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(8.dp)
                            )
                        },
                        onClick = { onYearSelected(year) }
                    )
                }
            }
        }
    }
}

@Composable
fun DaysOfWeekRow() {
    // Gün adlarını DayOfWeek üzerinden alıp kontrol ediyoruz.
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DayOfWeek.values().forEach { day ->
            Text(
                text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                // Font boyutunu artırdık, hafta sonu için error rengi kullanılıyor.
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                color = if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
                    MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CalendarGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onDayClick: (LocalDate) -> Unit
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value
    // currentMonth'e bağlı olarak listeyi hesaplıyoruz; boş hücreler de dahil.
    val daysList = remember(currentMonth) {
        (1..<daysInMonth + firstDayOfMonth).map { index ->
            if (index < firstDayOfMonth) null else index - firstDayOfMonth + 1
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        itemsIndexed(daysList, key = { index, _ -> index }) { index, day ->
            val cellDate = day?.let { LocalDate.of(currentMonth.year, currentMonth.month, it) }
            val isSelected = cellDate != null && selectedDate != null && cellDate == selectedDate
            val isWeekend = cellDate?.dayOfWeek == DayOfWeek.SATURDAY || cellDate?.dayOfWeek == DayOfWeek.SUNDAY
            ResponsiveDayCell(
                day = day,
                isSelected = isSelected,
                isWeekend = isWeekend,
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

@Composable
fun ResponsiveDayCell(
    day: Int?,
    isSelected: Boolean,
    isWeekend: Boolean,
    onClick: () -> Unit
) {
    BoxWithConstraints {
        // Ekran genişliğine göre hücre boyutunu ayarlıyoruz
        val cellSize = if (maxWidth < 300.dp) 40.dp else 48.dp
        val fontSize = if (cellSize == 40.dp) 14.sp else 16.sp
        // Seçili gün için border-radius'u arttırıyoruz
        val borderRadius = if (isSelected) 16.dp else 8.dp
        Box(
            modifier = Modifier
                .size(cellSize)
                .padding(3.dp)
                .background(
                    color = when {
                        day == null -> Color.Transparent
                        isSelected -> mainColor
                        else -> backgroundColor
                    },
                    shape = RoundedCornerShape(borderRadius)
                )
                .clickable(enabled = day != null) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day?.toString() ?: "",
                fontSize = fontSize,
                fontWeight = FontWeight.SemiBold,
                color = if (isSelected) backgroundColor else if (isWeekend) Color.Gray else Color.Black,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}
