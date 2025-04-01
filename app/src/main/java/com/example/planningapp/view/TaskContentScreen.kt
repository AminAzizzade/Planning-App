package com.example.planningapp.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TaskLabel
import com.example.planningapp.ui.theme.containerColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.textColor
import com.example.planningapp.view.partialview.general.ContainerTextView
import com.example.planningapp.view.viewmodel.ContentOfTaskViewModel


/**
 * TaskContentScreen: Ana ekran
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContentScreen(
    viewModel: ContentOfTaskViewModel,
    taskId: Int,
    navController: NavHostController
) {
    val allContents by viewModel.allTasks.observeAsState(mapOf())
    val contentState = allContents[taskId]

    if (contentState == null) {
        EmptyDataView(onAddClick = {
            val newContent = TaskContent(
                taskId = taskId,
                contentId = 0,
                missionNote = "",
                label = TaskLabel.HOME
            )
            viewModel.insertTaskContent(newContent)
            viewModel.resetViewModel()
        })
        return
    }

    var note by remember { mutableStateOf(contentState.missionNote) }
    var labelState by remember { mutableStateOf(contentState.label) }

    // Missions için ekleme ve anlık güncellemeler
    val allMissions by viewModel.allMissions.observeAsState(mapOf())
    val missionsFromDb = allMissions[contentState.contentId] ?: emptyList()

    val missions = remember { mutableStateListOf<CheckBoxMission>() }

    LaunchedEffect(missionsFromDb) {
        missions.clear()
        missions.addAll(missionsFromDb)
    }

    // Clean Code
    var changingData by remember { mutableIntStateOf(0) }

    LaunchedEffect(changingData)
    {
        viewModel.resetViewModel()
        viewModel.resetViewModel()

    }

    var updated by remember { mutableStateOf<TaskContent?>(null) }

    // Content kaydetme işlemi
    LaunchedEffect(updated) {
        updated?.let { content ->
            viewModel.updateTaskContent(content)
        }
        viewModel.resetViewModel()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            /**
            * Note giriş alanı
             */

            OutlinedTextField(
                value = note,
                onValueChange = {
                    if (it.length <= 250) note = it
                },
                label = { Text("Note") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                ,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = mainColor,
                    unfocusedBorderColor = textColor
                ),
                singleLine = false,
                maxLines = 5
            )


            /**
             * Label seçim dropdown menüsü
             */

            var expanded by remember { mutableStateOf(false) }
            val options = listOf(TaskLabel.WORK, TaskLabel.SCHOOL, TaskLabel.HOME)
            val shape2 = RoundedCornerShape(16.dp)

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = labelState.name,
                    onValueChange = {},
                    label = { Text("Label") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(12.dp),
                    shape = shape2,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = mainColor,
                        unfocusedBorderColor = textColor
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option.name) },
                            onClick = {
                                labelState = option
                                expanded = false
                            }
                        )
                    }
                }
            }


            /**
             * Yeni Mission ekleme alanı
             */


            val shape = RoundedCornerShape(4.dp)
            var missionName by remember { mutableStateOf("") }
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
                                // Yeni mission oluştur
                                val newMission = CheckBoxMission(
                                    missionId = 0,
                                    contentId = contentState.contentId,
                                    missionName = missionName,
                                    check = false
                                )
                                // Clean code
                                viewModel.insertMission(newMission)
                                changingData++
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


            /**
             * Mission listesini gösteren composable
             */

            Surface(
                modifier = Modifier
                    .height(500.dp)
                    .padding(8.dp)
            ) {
                LazyColumn {
                    itemsIndexed(missions) { index, mission ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                                .clip(RoundedCornerShape(12.dp))
                                .background(containerColor)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Checkbox(
                                checked = mission.check,
                                onCheckedChange = { newValue ->

                                    // Clean code
                                    if (newValue) {
                                        viewModel.checkMission(missions[index].missionId)
                                    } else {
                                        viewModel.uncheckMission(missions[index].missionId)
                                    }
                                    changingData++
                                },
                                colors = CheckboxDefaults.colors(
                                    mainColor
                                )
                            )
                            ContainerTextView(mission.missionName)
                            IconButton(
                                onClick = {
                                    // Clean code
                                    viewModel.deleteMission(mission)
                                    changingData++
                                }
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


            /**
             * Save butonu
             */
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .background(containerColor)
                ,
                colors = ButtonDefaults.buttonColors(mainColor),
                onClick = {
                    updated = TaskContent(
                        taskId = taskId,
                        contentId = contentState.contentId,
                        missionNote = note,
                        label = labelState
                    )

                    // Clean code
                    changingData++
                }
            ) {
                Text("Save")
            }
        }
    }
}


@Composable
fun EmptyDataView(onAddClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onAddClick,
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Data",
                modifier = Modifier.size(100.dp),
                tint = mainColor
            )
        }
    }
}