package com.example.planningapp.view.partialview._tcs

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.ui.theme.focusedColorOpacity
import com.example.planningapp.view.partialview.general.ContainerTextView
import com.example.planningapp.view.partialview.general.ContainerTextView_Checked
import com.example.planningapp.view.viewmodel.ContentOfTaskViewModel

@Composable
fun MissionList(
    missions: List<CheckBoxMission>,
    viewModel: ContentOfTaskViewModel,
    onDeleted: (CheckBoxMission) -> Unit,
    onChecked: (Boolean, Int) -> Unit
) {

    val unfocusedColor = focusedColorOpacity

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
    ) {
        itemsIndexed(missions) { index, mission ->
            var showDeleteDialog by remember { mutableStateOf(false) }

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Silme Onayı") },
                    text = { Text("“${mission.missionName}” görevini silmek istediğinize emin misiniz?") },
                    confirmButton = {
                        TextButton(onClick = {
                            onDeleted(mission)
                            showDeleteDialog = false
                        }) {
                            Text("Sil")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("İptal")
                        }
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    // Bütüne uzun basmayı yakala
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                showDeleteDialog = true
                            }
                        )
                    }
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Checkbox(
                    checked = mission.check,
                    onCheckedChange = { newValue ->
                        onChecked(
                            newValue,
                            index
                        )
                                      },
                    colors = CheckboxDefaults.colors(unfocusedColor)
                )

                if (mission.check) ContainerTextView_Checked(mission.missionName)
                else ContainerTextView(mission.missionName)

               Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }
}
