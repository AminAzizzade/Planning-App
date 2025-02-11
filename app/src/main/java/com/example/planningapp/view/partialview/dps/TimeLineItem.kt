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
    // Önceki görev var ise onun bitiş zamanını al, yoksa varsayılan bir değer ata.
    val previousEndTime = previousEvent?.endTime ?: -10

    val startTimeInt = event.startTime
    val endTimeInt = event.endTime
    val startTime = TimeConverterService.convert(startTimeInt)
    val endTime = TimeConverterService.convert(endTimeInt)

    // Görevin süresini hesaplıyoruz.
    // Burada event.endTime ve event.startTime arasındaki fark, görevin ne kadar sürdüğünü gösteriyor.
    // Eğer süre 0 ya da negatif çıkarsa, en az 1 zaman birimi kabul ediyoruz.
    val eventDuration = (event.endTime - event.startTime).coerceAtLeast(1)

    // Ölçek faktörü: Her bir zaman birimi için 80.dp yüksekliğe sahip olacak.
    val scaleFactor = 0.8
    // Görev kartının yüksekliğini dinamik olarak hesaplıyoruz.
    val cardHeight = (eventDuration * scaleFactor).dp

    // Zaman çizgisi (vertical bar) için, kart yüksekliğinden biraz fazla bir yükseklik kullanabiliriz;
    // orijinal kodda kart 80.dp, çizgi 90.dp idi. Benzer bir oranı burada da uyguluyoruz.
    val verticalBarHeight = cardHeight + 10.dp

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
    ) {
        // Eğer önceki görev bitiş zamanı, mevcut görev başlangıç zamanına eşit değilse,
        // başlangıç zamanını temsil eden işaretleri ekliyoruz.
        if (previousEndTime != startTimeInt) {
            Row {
                Spacer(modifier = Modifier.size(20.dp))
                Box(
                    modifier = Modifier
                        .width(4.dp)
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

            // Zaman çizgisi: yüksekliği görevin süresine göre ayarlanıyor.
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(verticalBarHeight)
                    .background(mainColor)
            )

            // Görev içeriğini barındıran kart: yüksekliği, görevin süresine göre dinamik olarak belirleniyor.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
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
                    // Görev adı
                    Text(
                        text = event.taskName,
                        color = backgroundColor,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 48.dp)
                    )

                    // Silme ve güncelleme ikonlarını içeren alan
                    Row(
                        modifier = Modifier
                            .fillMaxSize(0.5F)
                            .padding(1.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
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
        // Bitiş zamanını gösteren dairesel metin
        CircularText(text = endTime)
    }
}

@Composable
fun CircularText(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(25.dp)
            .width(40.dp)
            .background(
                mainColor,
                shape = androidx.compose.foundation.shape.CircleShape
            )
    ) {
        Text(
            text = text,
            color = backgroundColor,
            fontSize = 13.sp
        )
    }
}
