package com.example.planningapp.view.partialview._dps

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.planningapp.ui.theme.timeTextColor
import com.example.planningapp.view.Task
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel

/**
 * Bu composable, mevcut bir görevi güncellemek için bir pop-up (AlertDialog) sunar.
 *
 * @param viewModel Güncelleme işleminin tetikleneceği view model.
 * @param initialTask Güncellenecek mevcut görev nesnesi.
 * @param onDismiss Güncelleme pop-up'ı kapatıldığında çalışacak callback.
 * @param eventID Güncellenecek görevin ID'si.
 */
@Composable
fun TaskUpdatePopupScreen(
    viewModel: DailyPlanningViewModel,
    initialTask: Task,
    onDismiss: () -> Unit = {},
    eventID: Int,
) {
    var showPopup by remember { mutableStateOf(true) }
    var updatedTask by remember { mutableStateOf<Task?>(null) }

    // updatedTask değeri değiştiğinde, viewModel üzerinden güncelleme işlemi tetiklenir.
    LaunchedEffect(updatedTask) {
        updatedTask?.let { task ->
            viewModel.updateTimeLineTask(
                eventID,
                task.taskName,
                TimeConverterService.convert(task.startTime),
                TimeConverterService.convert(task.endTime)
            )
            updatedTask = null
            showPopup = true
            onDismiss()
        }
    }


    if (showPopup) {
        AlertDialog(
            onDismissRequest = {
                showPopup = false
                onDismiss()
            },
            title = { Text("Görevi Güncelle") },
            text = {
                // Form ekranında mevcut görevin bilgileri doldurularak güncelleniyor.
                taskUpdateFormScreen(
                    initialTask = initialTask,
                    onUpdate = { task ->
                        updatedTask = task
                    }
                )
            },
            confirmButton = {
                // Güncelle butonu form içerisindeki "Güncelle" butonuna entegredir.
            },
            dismissButton = {
                Button(onClick = {
                    showPopup = false
                    onDismiss()
                }) {
                    Text("Vazgeç")
                }
            }
        )
    }
    else{
        IconButton(
            onClick = {showPopup = true},
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Sil",
                tint = timeTextColor
            )
        }
    }
}

/**
 * Bu composable, güncellenecek görevin bilgilerini düzenlemek için bir form sunar.
 *
 * @param initialTask Form açılırken mevcut görev bilgilerini yükler.
 * @param onUpdate Kullanıcı "Güncelle" butonuna bastığında oluşturulan güncel Task nesnesini dışarıya döndürür.
 * @return Oluşturulan Task nesnesi (opsiyonel).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun taskUpdateFormScreen(
    initialTask: Task,
    onUpdate: (Task) -> Unit
): Task? {
    var taskName by remember { mutableStateOf(initialTask.taskName) }
    var startTime by remember { mutableStateOf(initialTask.startTime) }
    var endTime by remember { mutableStateOf(initialTask.endTime) }
    var task by remember { mutableStateOf<Task?>(null) }

    val context = LocalContext.current

    // Başlangıç saati seçimi için TimePickerDialog
    val startTimePicker = remember {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                startTime = String.format("%02d:%02d", hour, minute)
            },
            0, 0, true
        )
    }

    // Bitiş saati seçimi için TimePickerDialog
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
        Text(text = "Görevi Güncelle", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        // Görev Adı Girişi
        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Görev Adı") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Başlangıç Saati Girişi (TimePicker tetiklenir)
        OutlinedTextField(
            value = startTime,
            onValueChange = {},
            label = { Text("Başlangıç Saati") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { startTimePicker.show() },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Saat Seç",
                    modifier = Modifier.clickable { startTimePicker.show() }
                )
            }
        )

        // Bitiş Saati Girişi (TimePicker tetiklenir)
        OutlinedTextField(
            value = endTime,
            onValueChange = {},
            label = { Text("Bitiş Saati") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { endTimePicker.show() },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Saat Seç",
                    modifier = Modifier.clickable { endTimePicker.show() }
                )
            }
        )

        // Güncelle Butonu: Kullanıcı "Güncelle"ye tıklayarak güncel bilgileri gönderir.
        Button(
            onClick = {
                if (taskName.isNotBlank()) {
                    task = Task(taskName, startTime, endTime)
                    onUpdate(task!!)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Güncelle")
        }
    }

    return task
}
