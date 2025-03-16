package com.example.planningapp.view.partialview._cop.row

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planningapp.data.entity.project.HistoryLabel
import com.example.planningapp.data.entity.project.ProjectHistory
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.view.partialview.general.NormalTextView

@Composable
fun HistoryRow(
    historyItem: ProjectHistory, // Proje geçmişi modelinizin tipi
    onDelete: () -> Unit,
    mainColor: Color,
    textColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 16.dp)
        ) {
            val color = if (historyItem.historyLabel == HistoryLabel.BAD) Color.Red else if (historyItem.historyLabel == HistoryLabel.MODERATE) Color.Yellow else Color.Green

            // Daire simgesi (pozitif için yeşil, negatif için kırmızı)
            Box(
                modifier = Modifier
                    .size(40.dp) // Dış çerçeve boyutu
                    .shadow(3.dp, CircleShape) // Gölge ekleniyor
                    .clip(CircleShape)
                    .border(5.dp, mainColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .shadow(2.dp, CircleShape) // İç box için de hafif gölge
                        .clip(CircleShape)
                        .background(color)
                )
            }

            Spacer(
                modifier = Modifier
                    .width(5.dp)
                    .height(40.dp)
                    .background(mainColor)
                    .shadow(2.dp, CircleShape) // İç box için de hafif gölge
            )
        }

        NormalTextView(
            text = historyItem.projectHistoryName,
            color = textColor,
            modifier = Modifier.weight(1f),
            fontSize = 18
        )

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Sil",
                tint = focusedColor
            )
        }
    }
}