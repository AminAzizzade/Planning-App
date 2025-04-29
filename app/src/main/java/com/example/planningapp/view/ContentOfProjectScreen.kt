package com.example.planningapp.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.planningapp.data.entity.project.ProjectHistory
import com.example.planningapp.data.entity.project.ProjectTask
import com.example.planningapp.ui.theme.containerColor
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.textColor
import com.example.planningapp.ui.theme.unfocusedColor
import com.example.planningapp.view.partialview._cop.ProjectDescriptionSection
import com.example.planningapp.view.partialview._cop.popup.ProjectDescriptionPopup
import com.example.planningapp.view.partialview._cop.popup.ProjectHistoryPopup
import com.example.planningapp.view.partialview._cop.popup.ProjectTaskPopup
import com.example.planningapp.view.partialview._cop.row.HistoryRow
import com.example.planningapp.view.partialview._cop.row.TaskRow
import com.example.planningapp.view.partialview.general.NormalTextView
import com.example.planningapp.view.viewmodel.ContentOfProjectViewModel

@Composable
fun ContentOfProjectScreen(
    viewModel: ContentOfProjectViewModel,
    projectId: Int,
    navController: NavHostController
) {
    val history            by viewModel.history.observeAsState(initial = emptyList())
    val task               by viewModel.tasks.observeAsState(initial = emptyList())
    val projectDescription by viewModel.projectDescription.observeAsState(initial = null)

    var historyController by remember { mutableIntStateOf(0) }
    var taskController by remember { mutableIntStateOf(0) }
    var descriptionController by remember { mutableIntStateOf(0) }

    LaunchedEffect(historyController) { viewModel.resetViewModel() }
    LaunchedEffect(taskController) { viewModel.resetViewModel() }
    LaunchedEffect(descriptionController) { viewModel.resetViewModel() }

    var selectList by remember { mutableStateOf("history") }

    var showHistoryPopUp by remember { mutableStateOf(false) }
    var showTaskPopUp by remember { mutableStateOf(false) }
    var showDescriptionPopup by remember { mutableStateOf(false) }

    val containerColor = containerColor
    val focusedColor = focusedColor

    val unfocusedColor = unfocusedColor
    val iconColor = mainColor

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = containerColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(0.3F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Spacer(Modifier.size(30.dp))

                NormalTextView(text = "Project Name", color = textColor)

                projectDescription?.let {
                    ProjectDescriptionSection(
                        projectDescription = it,
                        onEditClick = { showDescriptionPopup = true }
                    )
                }
            }

            SelectionTab(
                selectedList = selectList,
                onSelect = { selectList = it },
                focusColor = focusedColor,
                unfocusedColor = unfocusedColor
            )

            HistoryAndTaskList(
                selectList = selectList,
                history = history,
                task = task,
                onHistoryDelete = { projectHistoryId ->
                    viewModel.deleteProjectHistory(projectHistoryId)
                    historyController++
                },
                onTaskDelete = { projectTaskId ->
                    viewModel.deleteProjectTask(projectTaskId)
                    taskController++
                },
                historyObjectColor = unfocusedColor,
                iconColor = iconColor
            )

            AddButton(
                onClick = {
                    if (selectList == "history") showHistoryPopUp = true
                    else showTaskPopUp = true
                }
            )

            if (showTaskPopUp) {
                ProjectTaskPopup(
                    projectId = projectId,
                    viewModel = viewModel,
                    onDismiss = { showTaskPopUp = false },
                    onTaskAdded = {
                        taskController++
                    }
                )
            }
            if (showHistoryPopUp) {
                ProjectHistoryPopup(
                    projectId = projectId,
                    viewModel = viewModel,
                    onDismiss = { showHistoryPopUp = false },
                    onHistoryAdded = {
                        historyController++
                    }
                )
            }
            if (showDescriptionPopup) {
                projectDescription?.let {
                    ProjectDescriptionPopup(
                        currentDescription = it.description,
                        onDismiss = { showDescriptionPopup = false },

                        onDescriptionUpdated = { updatedDesc ->

                            viewModel.updateProjectDescription(
                                projectDescriptionId = it.projectDescriptionId,
                                description = updatedDesc
                            )

                            descriptionController++

                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SelectionTab(
    selectedList: String,
    onSelect: (String) -> Unit,
    focusColor: Color,
    unfocusedColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.1F)
    ) {
        TabButton(
            text = "History",
            isSelected = selectedList == "history",
            onClick = { onSelect("history") },
            size = 0.5F,
            focusedColor = focusColor,
            unfocusedColor = unfocusedColor
        )
        TabButton(
            text = "Task",
            isSelected = selectedList == "task",
            onClick = { onSelect("task") },
            size = 1F,
            focusedColor = focusColor,
            unfocusedColor = unfocusedColor
        )
    }
}
@Composable
fun TabButton(
    size: Float,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    focusedColor: Color,
    unfocusedColor: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) focusedColor else unfocusedColor,
        animationSpec = tween(300),
        label = "backgroundColor"
    )

    val fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
    val fontSize = if (isSelected) 20.sp else 18.sp

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "scale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.7f else 1f,
        animationSpec = tween(100),
        label = "alpha"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth(size)
            .height(50.dp)
            .scale(scale)
            .alpha(alpha)
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        NormalTextView(
            text = text,
            fontWeight = fontWeight,
            fontSize = fontSize,
            color = Color.White
        )
    }
}

/*
    Proje ile ilgili yeni fikirler
    History kısmı
        biten görevler
        yeni haberler
    olarak ta ayrılabilir
 */

@Composable
fun HistoryAndTaskList(
    selectList: String,
    history: List<ProjectHistory>,
    task: List<ProjectTask>,
    onHistoryDelete: (projectHistoryId: Int) -> Unit,
    onTaskDelete: (projectTaskId: Int) -> Unit,
    historyObjectColor: Color,
    iconColor: Color
) {

    Surface(
        modifier = Modifier
            .fillMaxHeight(0.8F)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 4.dp,
        color = Color.White
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 6.dp)
        ) {
            if (selectList == "history") {
                items(history) { item ->
                    HistoryRow(
                        historyItem = item,
                        mainColor = historyObjectColor,
                        textColor = textColor,
                        onDelete = { onHistoryDelete(item.projectHistoryId) }
                    )
                }
            } else {
                items(task) { taskItem ->
                    TaskRow(
                        taskItem = taskItem,
                        onDelete = { onTaskDelete(taskItem.projectTaskId) },
                        iconColor = iconColor
                    )
                }
            }
        }
    }
}

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: Dp = 56.dp,
    iconSize: Dp = 28.dp,
    buttonColor: Color = focusedColor,
    contentDescription: String = "Yeni Geçmiş Ekle"
) {
    Box(modifier = modifier.padding(16.dp)) {
        Surface(
            modifier = Modifier
                .size(buttonSize)
                .shadow(4.dp, CircleShape),
            shape = CircleShape,
            tonalElevation = 4.dp,
            color = buttonColor
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = contentDescription,
                    tint = Color.White,
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}













