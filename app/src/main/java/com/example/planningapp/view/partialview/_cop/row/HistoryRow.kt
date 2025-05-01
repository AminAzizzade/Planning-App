package com.example.planningapp.view.partialview._cop.row

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.planningapp.data.entity.project.HistoryLabel
import com.example.planningapp.data.entity.project.ProjectHistory
import com.example.planningapp.view.partialview.general.NormalTextView

@Composable
fun HistoryRow(
    historyItem: ProjectHistory,
    onDelete: () -> Unit,
    mainColor: Color,
    textColor: Color
) {
    ConfirmableRow(
        itemName = historyItem.projectHistoryName,
        onDelete = onDelete
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            val innerColor = when (historyItem.historyLabel) {
                HistoryLabel.BAD -> Color.Red
                HistoryLabel.MODERATE -> Color.Yellow
                else -> Color.Green
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .shadow(3.dp, CircleShape)
                    .clip(CircleShape)
                    .border(5.dp, mainColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .shadow(2.dp, CircleShape)
                        .clip(CircleShape)
                        .background(innerColor)
                )
            }
            Spacer(
                modifier = Modifier
                    .width(5.dp)
                    .height(40.dp)
                    .background(mainColor)
                    .shadow(2.dp, CircleShape)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            NormalTextView(
                text = historyItem.projectHistoryName,
                color = textColor,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(37.dp))
        }
    }
}

