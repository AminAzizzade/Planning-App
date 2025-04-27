package com.example.planningapp.view.partialview._dps

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.service.TaskConverterService
import com.example.planningapp.service.TimeConverterService
import com.example.planningapp.ui.theme.containerColor
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.timeTextColor_beta
import com.example.planningapp.view.partialview.general.ContainerTextView
import com.example.planningapp.view.partialview.general.HoursTextView
import com.example.planningapp.view.partialview.general.TimeTextView
import com.example.planningapp.view.partialview.general.textColor_beta
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.ZoneId


@Composable
private fun ColoredLeftBar() {
    Box(
        modifier = Modifier
            .width(6.dp)
            .height(40.dp)
            .background(mainColor)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun getSecondsSinceMidnight(): Long {
    return LocalTime.now(ZoneId.systemDefault()).toSecondOfDay().toLong()
}


@Composable
private fun calculateOverlayText(
    currentTimeSec: Long,
    taskStartTimeMin: Int
): String {
    val taskStartTimeSec = taskStartTimeMin * 60L
    return if (currentTimeSec >= taskStartTimeSec) {
        "ongoing"
    } else {
        val remainingSec = taskStartTimeSec - currentTimeSec
        val hours = remainingSec / 3600
        val minutes = (remainingSec % 3600) / 60
        if (hours > 0)
            "${hours}h ${if (minutes < 10) "0" else ""}${minutes}min"
        else
            "${minutes}min left"
    }
}


@Composable
fun TaskOptionsDialog(
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onUpdate: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Görev Seçenekleri") },
        text = { Text("Bu görevde ne yapmak istersiniz?") },
        confirmButton = {
            IconButton(
                onClick = {
                    onUpdate()
                    onDismiss()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Güncelle"
                )
            }
        },
        dismissButton = {
            IconButton(
                onClick = {
                    onDelete()
                    onDismiss()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Sil"
                )
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimelineItem(
    viewModel: DailyPlanningViewModel,
    event: TimeLineTask,
    navController: NavHostController,
    isNextTask: Boolean = false,
    onChange: () -> Unit,
) {
    val startTimeInt = event.startTime
    val startTimeText = TimeConverterService.convert(startTimeInt)
    val endTimeText = TimeConverterService.convert(event.endTime)

    val taskHeight = if (isNextTask) 150.dp else 120.dp
    val taskWidthFactor = if (isNextTask) 0.9F else 0.85F
    val shadowElevation = if (isNextTask) 24.dp else 0.dp
    val cornerRadius = if (isNextTask) 20.dp else 36.dp

    var currentTimeSec by remember { mutableLongStateOf(getSecondsSinceMidnight()) }
    if (isNextTask) {
        LaunchedEffect(Unit) {
            while (true) {
                currentTimeSec = getSecondsSinceMidnight()
                delay(1000)
            }
        }
    }

    var showOptionsDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColoredLeftBar()

        Box(
            modifier = Modifier
                .fillMaxWidth(taskWidthFactor)
                .height(taskHeight)
                .shadow(
                    elevation = shadowElevation,
                    shape = RoundedCornerShape(cornerRadius),
                    clip = false
                )
                .background(
                    color = if (isNextTask) Color.White else Color(containerColor.value),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .combinedClickable(
                    onClick = { /* Navigasyon ok ikonu ile yapılacak */ },
                    onLongClick = { showOptionsDialog = true }
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    TimeTextView(
                        startTimeText,
                        timeTextColor = if (isNextTask) focusedColor else timeTextColor_beta
                    )
                    TimeTextView(
                        endTimeText,
                        timeTextColor = if (isNextTask) focusedColor else timeTextColor_beta
                    )
                }

                ContainerTextView(
                    event.taskName,
                    modifier = Modifier
                        .fillMaxWidth(0.55F),
                    textColor = if (isNextTask) focusedColor else textColor_beta
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.End
                ) {
                    if (isNextTask)
                    {
                        val overlayText = calculateOverlayText(currentTimeSec, startTimeInt)
                        HoursTextView(overlayText)
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Navigate to task content",
                        modifier = Modifier
                            .fillMaxHeight(0.35F)
                            .fillMaxWidth(0.6F)
                            .clickable {
                            navController.navigate("content/${event.taskID}")
                        },
                        tint = if (isNextTask) focusedColor else timeTextColor_beta
                    )

                    if (isNextTask) {
                        Spacer(
                            modifier = Modifier
                                .height(18.dp)
                                .width(8.dp)
                        )
                    }
                }


            }
        }
    }

    if (showOptionsDialog) {
        TaskOptionsDialog(
            onDismiss = { showOptionsDialog = false },
            onDelete = {
                viewModel.deleteTimeLineTask(event.taskID)
                onChange()
            },
            onUpdate = { showUpdateDialog = true }
        )
    }

    if (showUpdateDialog) {
        TaskUpdatePopupScreen(
            viewModel = viewModel,
            initialTask = TaskConverterService.convertToTask(event),
            eventID = event.taskID,
            onDismiss = { showUpdateDialog = false },
            onUpdate = { onChange() }
        )
    }
}