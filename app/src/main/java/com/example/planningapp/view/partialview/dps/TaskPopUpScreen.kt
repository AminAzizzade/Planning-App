package com.example.planningapp.view.partialview.dps

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planningapp.service.TimeConverterService
import com.example.planningapp.view.Task
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel

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
            viewModel.insertTimeLineTask(
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
