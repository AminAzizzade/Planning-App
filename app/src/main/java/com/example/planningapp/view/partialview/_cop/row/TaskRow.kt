package com.example.planningapp.view.partialview._cop.row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planningapp.data.entity.project.ProjectTask

@Composable
fun TaskRow(
    taskItem: ProjectTask,
    onDelete: () -> Unit,
    iconColor: Color
) {
    ConfirmableRow(
        itemName = taskItem.taskName,
        confirmTitle = "Görevi Sil",
        confirmMessage = "“${taskItem.taskName}” görevini silmek istediğinizden emin misiniz?",
        onDelete = onDelete
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Görev İkonu",
            modifier = Modifier.size(24.dp),
            tint = iconColor
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = taskItem.taskName,
            modifier = Modifier.weight(1f)
        )
    }
}

