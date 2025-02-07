package com.example.planningapp.view.partialview.dps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.service.TimeConverterService
import com.example.planningapp.ui.theme.backgroundColor
import com.example.planningapp.ui.theme.mainColor

@Composable
fun TimelineItem(
    event: TimeLineTask,
    previousEvent: TimeLineTask?,
    onDelete: () -> Unit   // Silme işlemini tetikleyecek lambda parametresi
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
        // Eğer önceki görev bitiş saati mevcut görev başlangıç saatiyle aynı değilse,
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

            // Görev içeriğini barındıran kutu (card) içerisine hem görev adı
            // hem de silme butonu (ikon) yerleştirilmiştir.
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
                // Görev adı solda ortalanmış şekilde yer alır.
                Text(
                    text = event.taskName,
                    color = backgroundColor,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
                // Silme işlemini tetikleyen ikon buton, sağ üst köşede konumlandırılır.
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Sil",
                        tint = backgroundColor
                    )
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
                shape = androidx.compose.foundation.shape.CircleShape
            )
    ) {
        Text(
            text = text,
            color = backgroundColor,
            fontSize = 16.sp
        )
    }
}
