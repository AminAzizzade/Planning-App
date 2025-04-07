package com.example.planningapp.view.partialview._ps

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun DeleteProjectDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Projeyi Sil") },
        text = { Text(text = "Bu projeyi silmek istediğinizden emin misiniz?") },
        confirmButton = {
            IconButton(onClick = onConfirm) {
                Text(text = "Evet")
            }
        },
        dismissButton = {
            IconButton(onClick = onDismiss) {
                Text(text = "Hayır")
            }
        }
    )
}