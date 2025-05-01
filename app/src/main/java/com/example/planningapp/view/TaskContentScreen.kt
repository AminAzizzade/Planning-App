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
import com.example.planningapp.view.partialview._tcs.MissionInput
import com.example.planningapp.view.partialview._tcs.MissionList
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
            .fillMaxHeight()
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                //.padding(top = 50.dp)
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
                    viewModel = viewModel,
                    onDeleted = { mission ->
                        viewModel.deleteMission(mission)
                        changingData++
                    },
                    onChecked = { newValue, index ->
                        if (newValue) {
                            viewModel.checkMission(missions[index].missionId)
                        } else {
                            viewModel.uncheckMission(missions[index].missionId)
                        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInputField(
    note: String,
    onNoteChange: (String) -> Unit
) {
    var showEditor by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            //.height(100.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        // ➊ Sabit yükseklikli, iç scroll’lu alan
        Box(
            modifier = Modifier
                .fillMaxWidth()         // önizlemenin yüksekliği
                .verticalScroll(scrollState)  // uzun metni içe kaydır
        ) {
            OutlinedTextField(
                value = note,
                onValueChange = {},       // readOnly
                readOnly = true,
                modifier = Modifier.fillMaxSize(),
                label = { Text("Note") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit note",
                        tint = mainColor
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = outlinedTextFieldColors(
                    disabledTextColor      = textColor_beta,
                    unfocusedBorderColor   = textColor_beta,
                    unfocusedLabelColor    = textColor_beta
                ),
                singleLine = true,
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    detectTapGestures { showEditor = true }
                }
        )
    }

    if (showEditor) {
        FullScreenNoteEditor(
            initialText = note,
            onDismiss   = { showEditor = false },
            onSave      = {
                onNoteChange(it)
                showEditor = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenNoteEditor(
    initialText: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var tempText by remember { mutableStateOf(initialText) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Not Düzenle", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = tempText,
                    onValueChange = {
                        if (it.length <= 250) tempText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    placeholder = { Text("Notunuzu buraya yazın...") },
                    maxLines = 20,
                    shape = RoundedCornerShape(8.dp),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = mainColor,
                        cursorColor        = mainColor
                    )
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("İptal")
                    }
                    Spacer(Modifier.width(8.dp))
                    TextButton(onClick = { onSave(tempText) }) {
                        Text("Kaydet")
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelDropdownMenu(
    labelState: TaskLabel,
    onLabelSelected: (TaskLabel) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = TaskLabel.entries
    val shape = RoundedCornerShape(16.dp)

    fun TaskLabel.icon() = when (this) {
        TaskLabel.WORK   -> Icons.Default.AccountBox
        TaskLabel.SCHOOL -> Icons.Default.Email
        TaskLabel.HOME   -> Icons.Default.Home
    }


    ExposedDropdownMenuBox(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth(0.8F),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = labelState.name,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
            ,
            shape = shape,
            label = { Text("Label") },
            leadingIcon = {
                Icon(
                    imageVector = labelState.icon(),
                    contentDescription = null,
                    tint = mainColor
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = outlinedTextFieldColors(
                focusedBorderColor   = mainColor,
                unfocusedBorderColor = textColor_beta,
                focusedLabelColor    = mainColor,
                unfocusedLabelColor  = textColor_beta
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, shape)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = option.icon(),
                                contentDescription = null,
                                tint = mainColor,
                                modifier = Modifier
                                    .size(20.dp)
                                    .fillMaxSize()
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(option.name)
                        }
                    },
                    onClick = {
                        onLabelSelected(option)
                        expanded = false
                    },
                    colors = MenuItemColors(
                        textColor = MaterialTheme.colorScheme.onSurface,
                        leadingIconColor = mainColor,
                        trailingIconColor =
                            if (option == labelState) mainColor.copy(alpha = 0.1f)
                            else  Color.Transparent,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledLeadingIconColor = mainColor,
                        disabledTrailingIconColor = mainColor,
                    ),
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }
    }
}




