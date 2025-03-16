package com.example.planningapp.view.partialview._cop.row

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planningapp.data.entity.project.ProjectTask
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.ui.theme.mainColor

@Composable
fun TaskRow(
    taskItem: ProjectTask, // Task modelinizin tipi
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Görev İkonu",
            modifier = Modifier.size(24.dp),
            tint = mainColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = taskItem.taskName,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Görevi Sil",
                tint = focusedColor
            )
        }
    }
}