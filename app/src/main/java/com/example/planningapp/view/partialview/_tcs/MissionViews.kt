package com.example.planningapp.view.partialview._tcs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.ui.theme.containerColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.view.partialview.general.ContainerTextView


/**
 * Yeni Mission ekleme alanı
 */
@Composable
fun MissionInput(contentId: Int, onNewMission: (String) -> Unit) {
    var missionName by remember { mutableStateOf("") }
    val shape = RoundedCornerShape(4.dp)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(shape)
            .border(width = 1.dp, color = Color.Transparent, shape = shape)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()

            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier.padding(4.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = mainColor,
                    focusedIndicatorColor = mainColor
                ),
                value = missionName,
                onValueChange = { missionName = it },
                label = { Text("Mission Name") },
            )

            IconButton(
                onClick = {
                    if (missionName.isNotBlank()) {
                        onNewMission(missionName)
                        missionName = ""
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Icon",
                    tint = mainColor
                )
            }
        }
    }
}

/**
 * Mission listesini gösteren composable
 */
@Composable
fun MissionList(
    missions: List<CheckBoxMission>,
    onMissionCheckedChange: (Int, Boolean) -> Unit,
    onMissionRemove: (CheckBoxMission) -> Unit
) {
    val shape = RoundedCornerShape(12.dp)

    Surface(
        modifier = Modifier
            .height(500.dp)
            .padding(8.dp)
    ) {
        LazyColumn {
            itemsIndexed(missions) { index, mission ->
                var missionChecked by remember { mutableStateOf(mission.check) }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .shadow(elevation = 4.dp, shape = shape)
                        .clip(shape)
                        .background(containerColor)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Checkbox(
                        colors = CheckboxColors(
                            checkedCheckmarkColor = mainColor,
                            uncheckedCheckmarkColor = mainColor,
                            checkedBorderColor = mainColor,
                            uncheckedBorderColor = mainColor,
                            checkedBoxColor = Color.Transparent,
                            uncheckedBoxColor = Color.Transparent,
                            disabledCheckedBoxColor = Color.White,
                            disabledUncheckedBoxColor = mainColor,
                            disabledIndeterminateBoxColor = mainColor,
                            disabledBorderColor = mainColor,
                            disabledIndeterminateBorderColor = mainColor,
                            disabledUncheckedBorderColor = mainColor
                        ),
                        checked = missionChecked,
                        onCheckedChange = { newValue ->
                            missionChecked = newValue
                            onMissionCheckedChange(index, newValue)
                        }
                    )

                    ContainerTextView(mission.missionName)

                    IconButton(
                        onClick = { onMissionRemove(mission) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete Icon",
                            tint = mainColor
                        )
                    }
                }
            }
        }
    }
}
