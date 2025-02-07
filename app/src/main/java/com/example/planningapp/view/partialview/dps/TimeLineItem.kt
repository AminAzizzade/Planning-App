package com.example.planningapp.view.partialview.dps

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.service.TaskConverterService
import com.example.planningapp.service.TimeConverterService
import com.example.planningapp.ui.theme.backgroundColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel

@Composable
fun TimelineItem(
    viewModel: DailyPlanningViewModel,
    event: TimeLineTask,
    previousEvent: TimeLineTask?,
    onDelete: () -> Unit, // Silme işlemini tetikleyecek lambda
) {
    var previousEndTime = -10
    if (previousEvent != null) previousEndTime = previousEvent.endTime

    val startTimeInt = event.startTime
    val endTimeInt = event.endTime
    val startTime = TimeConverterService.convert(startTimeInt)
    val endTime = TimeConverterService.convert(endTimeInt)

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
    ) {
        // Eğer önceki görev bitiş zamanı mevcut görev başlangıç zamanı ile aynı değilse,
        // başlangıç zamanı göstergesi oluşturulur.
        if (previousEndTime != startTimeInt) {
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(20.dp))

            Box(
                modifier = Modifier
                    .width(15.dp)
                    .height(90.dp)
                    .background(mainColor)
            )

            // Görev içeriğini barındıran kutu (card) içerisine, görev adı ve
            // güncelleme ile silme ikonlarının yer aldığı bölüm eklenmiştir.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(start = 18.dp, end = 16.dp)
                    .background(
                        color = Color(0xFFFF9800),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Görev adı, kutunun sol tarafında ortalanmış olarak gösterilir.
                    Text(
                        text = event.taskName,
                        color = backgroundColor,
                        fontSize = 16.sp,
                        modifier = Modifier
                            //.align(Alignment.CenterStart)
                            // Sağ tarafta ikonlar ile çakışmaması için ek padding
                            .padding(start = 16.dp, end = 48.dp)
                    )

                    // Güncelleme ve silme ikonlarını barındıran satır, kutunun sağ üst köşesinde yer alır.
                    Row(
                        modifier = Modifier
                            .fillMaxSize(0.5F)
                            .padding(1.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically

                            //.align(Alignment.BottomEnd)
                    ) {

                        TaskDeletePopUpScreen(viewModel, event.taskID)

                        TaskUpdatePopupScreen(
                            initialTask = TaskConverterService.convertToTask(event),
                            eventID = event.taskID,
                            onDismiss = {},
                            viewModel = viewModel
                        )

                    }
                }


            }
        }
        CircularText(text = endTime)
    }
}

@Composable
fun CircularText(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(40.dp)
            .width(60.dp)
            .background(
                mainColor,
                shape = androidx.compose.foundation.shape.CircleShape // Yuvarlak şekil
            )
    ) {
        Text(
            text = text,
            color = backgroundColor,
            fontSize = 16.sp
        )
    }
}
