package com.example.planningapp.view

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planningapp.ui.theme.containerColor
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.textColor
import com.example.planningapp.view.partialview._cop.popup.ProjectDescriptionPopup
import com.example.planningapp.view.partialview._cop.popup.ProjectHistoryPopup
import com.example.planningapp.view.partialview._cop.popup.ProjectTaskPopup
import com.example.planningapp.view.partialview.general.NormalTextView
import com.example.planningapp.view.viewmodel.ContentOfProjectViewModel

@Composable
fun ContentOfProjectScreen(
    viewModel: ContentOfProjectViewModel,
    projectId: Int,
    navController: NavHostController
) {
    val history by viewModel.history.observeAsState(initial = emptyList())
    val task by viewModel.tasks.observeAsState(initial = emptyList())

    var historyController by remember { mutableIntStateOf(0) }
    var taskController by remember { mutableIntStateOf(0) }

    LaunchedEffect(historyController) { viewModel.resetViewModel() }
    LaunchedEffect(taskController) { viewModel.resetViewModel() }

    var selectList by remember { mutableStateOf("history") }

    var showHistoryPopUp by remember { mutableStateOf(false) }
    var showTaskPopUp by remember { mutableStateOf(false) }
    var showDescriptionPopup by remember { mutableStateOf(false) }

    // Açıklama metnini tutacak state, başlangıçta boş
    var description by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = containerColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Üst başlık alanı
            Column(
                modifier = Modifier.fillMaxHeight(0.3F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Spacer(Modifier.size(30.dp))

                NormalTextView(text = "Project Name", color = textColor)

                ProjectDescriptionSection(
                    description = description,
                    onEditClick = { showDescriptionPopup = true }
                )
            }
            // History / Task seçim butonları
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1F)
            ) {
                val historyColor = if (selectList == "history") focusedColor else mainColor
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5F)
                        .height(50.dp)
                        .background(historyColor),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        modifier = Modifier
                            .width(90.dp)
                            .height(50.dp),
                        onClick = { selectList = "history" }
                    ) {
                        NormalTextView(text = "History")
                    }
                }
                val taskColor = if (selectList == "task") focusedColor else mainColor
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(taskColor)
                ) {
                    IconButton(
                        modifier = Modifier
                            .width(90.dp)
                            .height(50.dp)
                            .background(taskColor),
                        onClick = { selectList = "task" }
                    ) {
                        NormalTextView(text = "Task")
                    }
                }
            }
            // Liste için yuvarlak kenarlı scroll alanı
            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.8F)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 4.dp,
                color = Color.White
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(start = 6.dp, end = 6.dp, top = 6.dp)
                ) {
                    if (selectList == "history") {
                        items(history ?: emptyList()) { item ->
                            HistoryRow(
                                historyItem = item,
                                mainColor = mainColor,
                                textColor = textColor,
                                onDelete = {
                                    viewModel.deleteProjectHistory(item.projectHistoryId)
                                    historyController++
                                }
                            )
                        }
                    } else {
                        items(task ?: emptyList()) { taskItem ->
                            TaskRow(
                                taskItem = taskItem,
                                onDelete = {
                                    viewModel.deleteProjectTask(taskItem.projectTaskId)
                                    taskController++
                                }
                            )
                        }
                    }
                }
            }
            // Ekleme butonu
            Box(modifier = Modifier.padding(16.dp)) {
                Surface(
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(4.dp, CircleShape),
                    shape = CircleShape,
                    tonalElevation = 4.dp,
                    color = focusedColor
                ) {
                    IconButton(
                        onClick = {
                            if (selectList == "history") showHistoryPopUp = true
                            else showTaskPopUp = true
                        },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Yeni Geçmiş Ekle",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
            // Popup'lar
            if (showTaskPopUp) {
                ProjectTaskPopup(
                    projectId = projectId,
                    viewModel = viewModel,
                    onDismiss = { showTaskPopUp = false },
                    onTaskAdded = {
                        taskController++
                        Log.d("deneme1", "onTaskAdded: $taskController")
                    }
                )
            }
            if (showHistoryPopUp) {
                ProjectHistoryPopup(
                    projectId = projectId,
                    viewModel = viewModel,
                    onDismiss = { showHistoryPopUp = false },
                    onHistoryAdded = {
                        historyController++
                        Log.d("deneme1", "onHistoryAdded: $historyController")
                    }
                )
            }
            if (showDescriptionPopup) {
                ProjectDescriptionPopup(
                    currentDescription = description,
                    onDismiss = { showDescriptionPopup = false },
                    onDescriptionUpdated = { updatedDesc ->
                        description = updatedDesc
                    }
                )
            }
        }
    }
}











