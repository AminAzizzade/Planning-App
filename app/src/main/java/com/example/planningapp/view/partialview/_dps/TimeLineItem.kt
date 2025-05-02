package com.example.planningapp.view.partialview._dps

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.planningapp.data.entity.TaskStatus
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.service.TaskConverterService
import com.example.planningapp.service.TimeConverterService
import com.example.planningapp.ui.theme.containerColor
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.passiveContainerColor
import com.example.planningapp.ui.theme.timeTextColor_beta
import com.example.planningapp.ui.theme.timeTextColor_passive
import com.example.planningapp.view.partialview.general.ContainerTextView
import com.example.planningapp.view.partialview.general.HoursTextView
import com.example.planningapp.view.partialview.general.TimeTextView
import com.example.planningapp.view.partialview.general.textColor_beta
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.ZoneId

val cancelledColor = Color(0xFFFF7B93)

enum class TextColor(val value: Int, val color: Color) {
    IS_CANCELLED(-2, Color.White),
    IS_BEFORE(-1, timeTextColor_beta),
    IS_NEXT(0, focusedColor),
    IS_AFTER(1, timeTextColor_passive),
    ;

    companion object {
        fun getColorFor(value: Int, default: Color = Color.Gray): Color =
            TextColor.entries.firstOrNull { it.value == value }?.color ?: default
    }
}

enum class BackGroundColor(val value: Int, val color: Color) {
    IS_CANCELLED(-2, cancelledColor),
    IS_BEFORE(-1, containerColor),
    IS_NEXT(0, Color.White),
    IS_AFTER(1, passiveContainerColor),
    ;

    companion object {
        fun getColorFor(value: Int, default: Color = Color.Gray): Color =
            BackGroundColor.entries.firstOrNull { it.value == value }?.color ?: default
    }
}

enum class IconColor(val value: Int, val color: Color) {
    CANCELLED(-2, cancelledColor),
    COMPLETED(1, Color.White),
    UNSPECIFIED(-1, containerColor),
    NOW(0, mainColor),
    ;

    companion object {
        fun getColorFor(value: Int, default: Color = Color.Gray): Color =
            IconColor.entries.firstOrNull { it.value == value }?.color ?: default
    }
}

enum class BoxColor(val value: Int, val color: Color) {
    CANCELLED(-2, Color.White),
    COMPLETED(2, focusedColor),
    UNSPECIFIED_PAST(-1, mainColor),
    UNSPECIFIED_NOW(0, mainColor),
    UNSPECIFIED_FUTURE(1, textColor_beta),
    ;

    companion object {
        fun getColorFor(value: Int, default: Color = Color.Gray): Color =
            BoxColor.entries.firstOrNull { it.value == value }?.color ?: default
    }
}

@Composable
private fun StateCircle(
    timeLineStatus: TaskStatus,
    index : Int
)
{
    val iconSize = if (index == 0) 18.dp else 16.dp
    var boxColorState = when (timeLineStatus) {
        TaskStatus.IS_CANCELLED -> -2
        TaskStatus.IS_COMPLETED -> 2
        else -> 0
    }
    if (boxColorState == 0) boxColorState = index
    var boxColor = BoxColor.getColorFor(boxColorState)

    Box(
        modifier = Modifier
            .background(boxColor, CircleShape)
            .size(iconSize)
        ,
        contentAlignment = Alignment.Center
    ) {
        if (timeLineStatus == TaskStatus.IS_UNSPECIFIED)
        {
            if (index == 1)
            {
                Box(
                    modifier = Modifier
                        .background(IconColor.getColorFor(-1), CircleShape)
                        .size(12.dp)
                    ,
                    contentAlignment = Alignment.Center
                ) {}
            }

            if (index == -1)
            {
                Box(
                    modifier = Modifier
                        .background(IconColor.getColorFor(-1), CircleShape)
                        .size(12.dp)
                    ,
                    contentAlignment = Alignment.Center
                ) {}
            }

        }
        else if(timeLineStatus == TaskStatus.IS_COMPLETED)
        {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Completed",
                tint = IconColor.getColorFor(1)
            )
        }
        else if(timeLineStatus == TaskStatus.IS_CANCELLED)
        {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cancelled",
                tint = IconColor.getColorFor(-2)
            )
        }

    }
}

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
    }
    else {
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
    status: Int
) {
    val isNeedSummary = status == -1 && event.status == TaskStatus.IS_UNSPECIFIED
    val isHaveSummary = status == -1 && event.status == TaskStatus.IS_COMPLETED

    Log.d("Summary", "event: ${event.taskName} status: $status \n" +
            "event status: ${event.status} \n"+
            " isNeedSummary: $isNeedSummary")
    val startTimeInt = event.startTime
    val startTimeText = TimeConverterService.convert(startTimeInt)

    val endTimeText = TimeConverterService.convert(event.endTime)

    val taskHeight = if (isNextTask) 150.dp else 120.dp
    val taskWidthFactor = if (isNextTask) 0.9F else 0.85F
    val shadowElevation = if (isNextTask) 24.dp else 0.dp
    val cornerRadius = if (isNextTask) 20.dp else 36.dp

    val textColor = TextColor.getColorFor(status)
    val containerColor = BackGroundColor.getColorFor(status)

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
                    color = containerColor,
                    shape = RoundedCornerShape(cornerRadius)
                )
                .combinedClickable(
                    onClick = {
                        navController.navigate("content/${event.taskID}")
                    },
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
                        .fillMaxWidth(0.25F)
                        .fillMaxHeight()
                    ,
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    TimeTextView(
                        startTimeText,
                        timeTextColor = textColor
                    )
                    TimeTextView(
                        endTimeText,
                        timeTextColor = textColor
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.66F)
                        .fillMaxHeight()
                        .padding(
                            0.dp, // start
                            8.dp, // top
                            0.dp, // end
                            0.dp  // bottom
                        )
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    StateCircle(
                        timeLineStatus = event.status,
                        index = status
                    )

                    ContainerTextView(
                        event.taskName,

                        modifier = Modifier
                        .fillMaxWidth(),

                        textColor = textColor
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }




                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                    ,
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isNextTask)
                    {
                        val overlayText = calculateOverlayText(currentTimeSec, startTimeInt)
                        HoursTextView(overlayText)
                    }

                    // Summary Kısmı yoksa burası görünümü ortalamak için
                    // çalışacak


                    if (isNeedSummary)
                    {
                        TaskStatusButton(
                            onComplete = {
                                viewModel.updateTaskStatus(event.taskID, TaskStatus.IS_COMPLETED)
                                onChange()
                            }
                            ,
                            onCancel   = {
                                viewModel.updateTaskStatus(event.taskID, TaskStatus.IS_CANCELLED)
                                onChange()
                            }
                        )
                    }else
                    {
                        Spacer(modifier = Modifier
                            .fillMaxHeight(0.35F)
                            .fillMaxWidth(0.6F)
                            //.height(18.dp)
                        )
                    }

//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
//                        contentDescription = "Navigate to task content",
//                        modifier = Modifier
//                            .fillMaxHeight(0.35F)
//                            .fillMaxWidth(0.6F)
//                            .clickable {
//                            navController.navigate("content/${event.taskID}")
//                        },
//                        tint = if (isNextTask) focusedColor else timeTextColor_beta
//                    )

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskStatusButton(
    modifier: Modifier = Modifier,
    onComplete: () -> Unit,
    onCancel: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    // ◀︎ Durum menüsünü açan buton
    IconButton(
        onClick = { showDialog = true },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Görev Durumu"
        )
    }

    // ◀︎ Seçim dialog'u
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Görev Durumu") },
            text = { Text("Bu görevi tamamlandı olarak mı, yoksa iptal olarak mı işaretlemek istiyorsunuz?") },
            confirmButton = {
                TextButton(onClick = {
                    onComplete()
                    showDialog = false
                }) {
                    Text("Tamamlandı")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onCancel()
                    showDialog = false
                }) {
                    Text("İptal Edildi")
                }
            }
        )
    }
}