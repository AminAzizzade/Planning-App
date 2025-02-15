package com.example.planningapp.view.partialview.dps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planningapp.data.entity.TimeLineTask
import com.example.planningapp.service.TaskConverterService
import com.example.planningapp.service.TimeConverterService
import com.example.planningapp.ui.theme.containerColor
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.textColor
import com.example.planningapp.ui.theme.timeLineCardColor
import com.example.planningapp.ui.theme.timeLineCardTextColor
import com.example.planningapp.ui.theme.timeTextColor
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel


@Composable
fun TimelineItem(
    viewModel: DailyPlanningViewModel,
    event: TimeLineTask,
    previousEvent: TimeLineTask?,
    onDelete: () -> Unit,
    onTaskClick: (Int) -> Unit
)
{

    val startTimeInt = event.startTime
    val endTimeInt = event.endTime
    val startTime = TimeConverterService.convert(startTimeInt)
    val endTime = TimeConverterService.convert(endTimeInt)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        Box(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp)
                .background(mainColor)
        )

        val shape = RoundedCornerShape(24.dp)
        // Görev Kartı
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    color = Color(containerColor.value),
                    shape = RoundedCornerShape(12.dp)
                )
                .shadow(elevation = 8.dp, shape = shape)
                .clickable { onTaskClick(event.taskID) }
            ,
            contentAlignment = Alignment.Center,
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(containerColor),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround
                )
                {
                    TimeTextView(startTime)
                    TimeTextView(endTime)
                }

                ContainerTextView(event.taskName)

                // Silme ve güncelleme ikonlarını içeren alan
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround
                )
                {
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
}

@Composable
fun TimeTextView(time: String)
{
    Text(
        text = time,
        color = focusedColor,
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Monospace,
    )
}

@Composable
fun ContainerTextView(text: String)
{
    Text(
        text = text,
        color = textColor,
        fontSize = 20.sp,
        //fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Default,
        //modifier = Modifier.padding(8.dp)
    )
}

