package com.example.planningapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TaskLabel
import com.example.planningapp.ui.theme.containerColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.view.partialview._tcs.EmptyDataView
import com.example.planningapp.view.partialview._tcs.LabelDropdownMenu
import com.example.planningapp.view.partialview._tcs.MissionInput
import com.example.planningapp.view.partialview._tcs.MissionList
import com.example.planningapp.view.partialview._tcs.NoteInputField
import com.example.planningapp.view.partialview.general.textColor_beta
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
        // Öncelikle işaretlenmemiş görevleri, sonra işaretli görevleri ekleyerek sıralama
        val unchecked = missionsFromDb.filter { !it.check }
        val checked = missionsFromDb.filter { it.check }
        missions.addAll(unchecked + checked)
    }

    // Clean Code
    var changingData by remember { mutableIntStateOf(0) }

    LaunchedEffect(changingData) {
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
            .fillMaxHeight()
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            NoteInputField(
                note = note,
                onNoteChange = { note = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LabelDropdownMenu(
                labelState = labelState,
                onLabelSelected = { labelState = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MissionInput(
                onAddMission = { missionName ->
                    if (missionName.isNotBlank()) {
                        val newMission = CheckBoxMission(
                            missionId = 0,
                            contentId = contentState.contentId,
                            missionName = missionName,
                            check = false
                        )
                        viewModel.insertMission(newMission)
                        changingData++
                    }
                }
            )

            /**
             * Mission listesini gösteren composable
             */
            Surface(
                modifier = Modifier
                    .background(Color.White)
                    .height(500.dp)
                    .padding(8.dp)
            ) {
                MissionList(
                    missions = missions,
                    onDeleted = { mission ->
                        viewModel.deleteMission(mission)
                        changingData++
                    },
                    onChecked = { newValue, index ->
                        if (newValue) viewModel.checkMission(missions[index].missionId)
                        else viewModel.uncheckMission(missions[index].missionId)
                        changingData++
                    }
                )
            }

            /**
             * Save butonu
             */
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .background(containerColor),
                colors = ButtonDefaults.buttonColors(mainColor),
                onClick = {
                    updated = TaskContent(
                        taskId = taskId,
                        contentId = contentState.contentId,
                        missionNote = note,
                        label = labelState
                    )
                    changingData++
                }
            ) {
                Text("Save")
            }
        }
    }
}




