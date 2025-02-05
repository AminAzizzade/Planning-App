package com.example.planningapp.view

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.service.DateExtractorService
import com.example.planningapp.service.TimeConverterService
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.view.datastructure.DailyTimeLineTasks
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

    var year by remember { mutableIntStateOf(1) }
    var month by remember { mutableIntStateOf(1) }
    var day by remember { mutableIntStateOf(1) }

    if (date != null) {
        year = DateExtractorService.getYear(date)
        month = DateExtractorService.getMonth(date)
        day = DateExtractorService.getDay(date)
    } else {
        println("Geçersiz tarih formatı!")
    }


    // Timeline verilerini tutan sınıf
    val dailyTimeLineTasks = DailyTimeLineTasks()

    // ViewModel'den belirli güne ait verileri çek
    viewModel.getOneDay(day, month, year)
    val timeLine = viewModel.oneDay.observeAsState()

    // Timeline verilerini çekerek listeye ekle
    try {
        timeLine.value?.let {
            dailyTimeLineTasks.addAllTimeLine(it.dailyTimeLine.timeLineList)
        }
    } catch (e: Exception) {
        Log.e("DailyPlanningScreen", "error: ${e.message}")
    } finally {
        Log.e("DailyPlanningScreen", "TimeLine count: ${dailyTimeLineTasks.timeLineList.size}")
    }




    //viewModel.testCodeInsert()
    //viewModel.testCodeRemove()

    val lastDayOfMonth = lastDayOfMonths[month - 1]
    val list = dailyTimeLineTasks.timeLineList

    // UI düzeni
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Gün seçici (5 günlük kaydırmalı liste)
        DaySelector(
            selectedDay = day,
            lastDayOfMonth = lastDayOfMonth,
            onDaySelected = { newDay -> day = newDay }
        )

        Spacer(modifier = Modifier
            .background(Color.Red)
            .size(10.dp)
        )

        // Görevlerin (timeline) gösterildiği bölüm
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.6f)
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
                }
            }
        }

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

@Composable
fun TaskPopupScreen(
    viewModel: DailyPlanningViewModel,
    day: Int,
    month: Int,
    year: Int
) {
    var showPopup by remember { mutableStateOf(false) }
    var savedTask by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(savedTask) {
        savedTask?.let { task ->
            viewModel.insertedTimeLineTask(
                day, month, year,
                task.taskName,
                TimeConverterService.convert(task.startTime),
                TimeConverterService.convert(task.endTime)
            )
            savedTask = null
        }
    }

    if (showPopup) {
        AlertDialog(
            onDismissRequest = { showPopup = false },
            title = { Text("Yeni Görev Ekle") },
            // 'text' alanının içinde formu gösterebiliriz
            text = {
                taskFormScreen(
                    onSave = { newTask ->
                        savedTask = newTask
                        showPopup = false
                    }
                )
            },
            confirmButton = {
                // Form içerisindeki "Kaydet" butonunu kullanmayıp,
                // AlertDialog'un butonlarını tercih edebilirsiniz.
                // Dilerseniz formun kendisindeki butonu iptal edip burayı kullanabilirsiniz.
            },
            dismissButton = {
                Button(onClick = { showPopup = false }) {
                    Text("Vazgeç")
                }
            }
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Görev eklemek için butona tıklayın",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showPopup = true }) {
                Text(text = "Görev Ekle")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun taskFormScreen(
    onSave: (Task) -> Unit // Kaydedilen görevi dışarıya döndüren callback
): Task? {  // Fonksiyonun döndüreceği nesne türünü belirttik
    // Form State'leri
    var taskName by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("00:00") }
    var endTime by remember { mutableStateOf("00:00") }
    var task by remember { mutableStateOf<Task?>(null) } // Task nesnesini oluşturup saklamak için

    val context = LocalContext.current

    // Time Picker for Start Time
    val startTimePicker = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                startTime = String.format("%02d:%02d", hour, minute)
            },
            0, 0, true
        )
    }

    // Time Picker for End Time
    val endTimePicker = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                endTime = String.format("%02d:%02d", hour, minute)
            },
            0, 0, true
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Yeni Görev Ekle", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        // Task Name Input
        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Görev Adı") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Start Time Picker
        OutlinedTextField(
            value = startTime,
            onValueChange = {},
            label = { Text("Başlangıç Saati") },
            readOnly = true, // Kullanıcı direkt olarak değiştiremesin
            modifier = Modifier
                .fillMaxWidth()
                .clickable { startTimePicker.show() }, // Saat seçim penceresini aç
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Saat Seç",
                    modifier = Modifier.clickable { startTimePicker.show() }
                )
            }
        )

        // End Time Picker
        OutlinedTextField(
            value = endTime,
            onValueChange = {},
            label = { Text("Bitiş Saati") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { endTimePicker.show() }, // Saat seçim penceresini aç
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Saat Seç",
                    modifier = Modifier.clickable { endTimePicker.show() }
                )
            }
        )

        // Kaydet Butonu
        Button(
            onClick = {
                if (taskName.isNotBlank()) {
//                    Log.e("MyActivity", "Task: name ${taskName} \n endTime ${endTime} \n startTime ${startTime}")
                    task = Task(taskName, startTime, endTime) // Task nesnesini oluştur
                    Log.d("TaskForm", "Task Oluşturuldu: $task") // Logda göster
                    onSave(task!!) // Onaylanan task nesnesini döndür
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Kaydet")
        }
    }

    //Log.e("MyActivity", "Task: name ${task?.taskName} \n endTime ${task?.endTime} \n startTime ${task?.startTime}")
    return task // Task nesnesini döndür
}




@Composable
fun DaySelector(
    lastDayOfMonth: Int,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    val days = remember(selectedDay) {
        List(5) { index ->
            val calculatedDay = selectedDay - 2 + index
            when {
                calculatedDay > lastDayOfMonth -> 0 // Ayın son gününden sonraki günler için 0
                else -> calculatedDay
            }
        }
    }


    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4285F4))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(days) { day ->
            DayItem(day, selectedDay, onDaySelected)
        }
    }
}

@Composable
fun DayItem(day: Int, selectedDay: Int, onDaySelected: (Int) -> Unit) {
    val isSelected = day == selectedDay
    val backgroundColor = if (isSelected) Color.White else Color.Transparent
    val textColor = if (isSelected) Color(0xFF4285F4) else Color.White
    val borderWidth = if (isSelected) 2.dp else 0.dp
    val borderColor = if (isSelected) Color(0xFF4285F4) else Color.Transparent

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { if (day > 0) onDaySelected(day) }
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        if (day > 0) {
            Text(
                text = day.toString(),
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun TimelineItem(event: TimeLineTask, previousEvent: TimeLineTask?)
{
    //Log.e("DailyPlanningScreen", "daily __ timeLine task name: ${event.taskName}")

    var previousEndTime = -10
//    if(previousEvent == null) return

    if (previousEvent != null) {
        previousEndTime = previousEvent.endTime
    }
    val startTimeInt = event.startTime
    val endTimeInt = event.endTime
    val startTime = TimeConverterService.convert(startTimeInt)
    val endTime = TimeConverterService.convert(endTimeInt)

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            //.fillMaxWidth()
    )
    {
        if(previousEndTime != startTimeInt)
        {
            Row {
                Spacer(modifier = Modifier.size(20.dp))
                Box(
                    modifier = Modifier
                        .width(15.dp)
                        .height(20.dp)
                        .background(mainColor)
                )
            }

            CircularText(text = startTime)
        }

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        )
        {
            Spacer(modifier = Modifier.size(20.dp))

            Box(
                modifier = Modifier
                    .width(15.dp)
                    .height(90.dp)
                    .background(mainColor)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(start = 18.dp)
                    .background(
                        color = Color(0xFFFF9800),
                        shape = RoundedCornerShape(12.dp)
                    )
                ,
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = event.taskName ,
                    color = Color.White,
                    fontSize = 16.sp
                )

//                Text(
//                    text = ,
//                    color = Color.White,
//                    fontSize = 16.sp
//                )
            }
        }

        CircularText(text = endTime)
    }
}

@Composable
fun CircularText(text: String)
{
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(40.dp)
            .width(60.dp)
            .background(
                mainColor,
                shape = androidx.compose.foundation.shape.CircleShape // Yuvarlak şekil
            )
    )
    {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )

    }
}

