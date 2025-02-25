package com.example.planningapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planningapp.data.entity.CheckBoxMission
import com.example.planningapp.data.entity.TaskContent
import com.example.planningapp.data.entity.TaskLabel
import com.example.planningapp.ui.theme.containerColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.view.partialview._tcs.LabelDropdownMenu
import com.example.planningapp.view.partialview._tcs.MissionInput
import com.example.planningapp.view.partialview._tcs.MissionList
import com.example.planningapp.view.partialview._tcs.NoteField
import com.example.planningapp.view.viewmodel.ContentOfTaskViewModel

enum class ContentMode {
    INSERT,
    UPDATE
}

/**
 * TaskContentScreen: Ana ekran
 */
@Composable
fun TaskContentScreen(viewModel: ContentOfTaskViewModel, taskId: Int) {
    // Verileri gözlemle
    val allContents = viewModel.allTasks.observeAsState()
    val allMissions = viewModel.allMissions.observeAsState()

    val contentState = remember { mutableStateOf(allContents.value?.get(taskId))  }
    val missionsState = remember { mutableStateOf(allMissions.value?.get(contentState.value?.contentId ?: 0)) }

    val contentModeState = viewModel.contentLabel.observeAsState()
    val missionModeState = viewModel.missionLabel.observeAsState()

    // Label state (varsayılan olarak HOME)
    val labelState = remember { mutableStateOf(contentState.value?.label ?: TaskLabel.HOME) }

    // Notu saklayan state
    var note by remember { mutableStateOf("") }
    if (contentModeState.value == ContentMode.UPDATE && contentState.value != null) {
        labelState.value = contentState.value!!.label
        note = contentState.value!!.missionNote
    }

    // Mission işlemleri için state'ler
    var insertedMission by remember { mutableStateOf<CheckBoxMission?>(null) }
    var updatedMission by remember {
        mutableStateOf(missionsState.value ?: emptyList())
    }
    var removedMission by remember { mutableStateOf<CheckBoxMission?>(null) }
    var savedContent by remember { mutableStateOf<TaskContent?>(null) }

    // Content kaydetme işlemi
    LaunchedEffect(savedContent) {
        savedContent?.let { content ->
            if (contentModeState.value == ContentMode.INSERT) {
                viewModel.insertTaskContent(content)
            } else {
                viewModel.updateTaskContent(content)
            }
        }
    }

    // Mission güncelleme işlemi
    LaunchedEffect(updatedMission) {
        if (missionModeState.value == ContentMode.UPDATE && updatedMission.isNotEmpty()) {
            viewModel.updateMissions(updatedMission)
        }
    }

    // Yeni mission ekleme işlemi
    LaunchedEffect(insertedMission) {
        insertedMission?.let { mission ->
            viewModel.insertMission(mission)
        }
    }

    // Mission silme işlemi
    LaunchedEffect(removedMission) {
        removedMission?.let { mission ->
            viewModel.deleteMission(mission)
        }
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
            // Note giriş alanı
            NoteField(note = note, onNoteChange = { note = it })

            // Label seçim dropdown menüsü
            LabelDropdownMenu(
                selectedLabel = labelState.value,
                onLabelSelected = { labelState.value = it }
            )

            // Yeni mission eklemek için input alanı ve buton
            MissionInput(
                contentId = contentState.value?.contentId ?: 0,
                onNewMission = { missionName ->
                    insertedMission = CheckBoxMission(
                        missionId = 0,
                        contentId = contentState.value?.contentId ?: 0,
                        missionName = missionName,
                        check = false
                    )
                }
            )

            // Mevcut mission'ların listesi
            MissionList(
                missions = updatedMission,
                onMissionCheckedChange = { index, newValue ->
                    updatedMission = updatedMission.toMutableList().also {
                        it[index] = it[index].copy(check = newValue)
                    }
                },
                onMissionRemove = { mission ->
                    removedMission = mission
                }
            )

            // Save butonu
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .background(containerColor)
                ,
                colors = ButtonDefaults.buttonColors(mainColor),
                onClick = {
                    savedContent = TaskContent(
                        taskId = taskId,
                        contentId = contentState.value?.contentId ?: 0,
                        missionNote = note,
                        label = labelState.value
                    )
                }
            ) {
                Text("Save")
            }
        }
    }
}
