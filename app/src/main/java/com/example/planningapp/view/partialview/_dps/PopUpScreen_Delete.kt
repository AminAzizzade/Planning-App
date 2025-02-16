package com.example.planningapp.view.partialview._dps


import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planningapp.ui.theme.timeTextColor
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel

/**
 * Bu composable, ekranda yer alan "Görevi Sil" butonuna tıklanmasıyla silme onay pop-up'ını tetikler.
 * Onay verildiğinde viewModel üzerinden silme işlemi gerçekleştirilir.
 *
 * @param viewModel Silme işleminin yapılacağı view model.
 * @param eventID Silinecek görevin ID'si.
 */
@Composable
fun TaskDeletePopUpScreen(
    viewModel: DailyPlanningViewModel,
    eventID: Int,
) {
    var showPopup by remember { mutableStateOf(false) }
    var confirmDeletion by remember { mutableStateOf(false) }

    // confirmDeletion true olduğunda viewModel üzerinden silme işlemi tetiklenir.
    LaunchedEffect(confirmDeletion) {
        if (confirmDeletion) {
            viewModel.deleteTimeLineTask(eventID)
            confirmDeletion = false
        }
    }

    if (showPopup) {
        // Silme işlemi öncesi onay pop-up'ı
        AlertDialog(
            onDismissRequest = { showPopup = false },
            title = { Text("Görevi Sil") },
            text = { Text("Bu görevi silmek istediğinize emin misiniz?") },
            confirmButton = {
                Button(
                    onClick = {
                        confirmDeletion = true
                        showPopup = false
                    }
                ) {
                    Text("Evet")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showPopup = false }
                ) {
                    Text("Vazgeç")
                }
            }
        )
    } else {
        // Pop-up kapalıyken ekranda "Görevi Sil" butonu gösterilir
        IconButton(
            onClick = {showPopup = true},
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Sil",
                tint = timeTextColor
            )
        }
    }
}

