package com.example.planningapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planningapp.data.entity.project.Project
import com.example.planningapp.ui.theme.backgroundColor
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.view.partialview._ps.DeleteProjectDialog
import com.example.planningapp.view.partialview._ps.NavigateProjectButton
import com.example.planningapp.view.partialview.general.IconList
import com.example.planningapp.view.partialview.general.NormalTextView
import com.example.planningapp.view.viewmodel.ProjectViewModel

@Composable
fun ProjectScreen(
    viewModel: ProjectViewModel,
    navController: NavHostController,
)
{
    LaunchedEffect(true) { viewModel.resetViewModel() }

    val projects = viewModel.projects.observeAsState()

    val list = ArrayList<Project>()
    if (projects.value != null)
    {
        for (project in projects.value!!.listIterator()) list.add(project)
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            IconList(
                navController,
                index = 0
            )

            LazyColumn(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                items(list) {
                    ProjectView(it, navController, viewModel)
                }
            }
        }
    }
}


@Composable
fun ProjectView(
    project: Project,
    navController: NavHostController,
    viewModel: ProjectViewModel
) {
    val viewShape = RoundedCornerShape(
        topStart = 8.dp,
        bottomStart = 8.dp,
        topEnd = 36.dp,
        bottomEnd = 36.dp,
    )

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        DeleteProjectDialog(
            onDismiss = { showDialog.value = false },
            onConfirm = {
                viewModel.deleteProject(project.projectId)
                showDialog.value = false
            }
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                elevation = 1.dp,
                shape = viewShape,
                clip = false,
                ambientColor = mainColor,
                spotColor = mainColor
            )
            .padding(2.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showDialog.value = true
                    }
                )
            },
        shape = viewShape,
        color = Color(mainColor.value)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(4.dp))

            NormalTextView(text = project.projectName)

            NavigateProjectButton(
                projectId = project.projectId,
                navController = navController
            )
        }
    }
}