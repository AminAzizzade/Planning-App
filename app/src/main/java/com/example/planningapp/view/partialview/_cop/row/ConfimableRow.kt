package com.example.planningapp.view.partialview._cop.row

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ConfirmableRow(
    itemName: String,
    confirmTitle: String = "Silme Onayı",
    confirmMessage: String = "“$itemName” öğesini silmek istediğinizden emin misiniz?",
    onDelete: () -> Unit,
    contentPadding: PaddingValues =
        //PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        PaddingValues(vertical = 0.dp, horizontal = 0.dp),
    content: @Composable RowScope.() -> Unit
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { setShowDialog(false) },
            title = { Text(confirmTitle) },
            text = { Text(confirmMessage) },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    setShowDialog(false)
                }) {
                    Text("Evet")
                }
            },
            dismissButton = {
                TextButton(onClick = { setShowDialog(false) }) {
                    Text("Hayır")
                }
            }
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { /* tek tık işlemi gerekirse buraya */ },
                onLongClick = { setShowDialog(true) }
            )
            .padding(contentPadding)
    ) {
        content()
    }
}
