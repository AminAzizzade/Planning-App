package com.example.planningapp.view

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planningapp.data.entity.project.Project
import com.example.planningapp.ui.theme.focusedColor
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(16.dp))

            IconListLazyRow(navController)

            Text("Projects")

            LazyColumn(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                items(list)
                {
                    ProjectView(it, navController)
                }
            }
        }

    }


}


@Composable
fun ProjectView(
    project: Project,
    navController: NavHostController
) {
    // Hem dış surface hem de icon button için ortak custom shape
    val viewShape = RoundedCornerShape(
        topStart = 8.dp,
        bottomStart = 8.dp,
        topEnd = 36.dp,
        bottomEnd = 36.dp,
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                elevation = 1.dp,
                shape = viewShape,
                clip = false,
                ambientColor = focusedColor,
                spotColor = focusedColor
            )
            .padding(2.dp),
        shape = viewShape,
        color = Color(focusedColor.value)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.size(4.dp))

            NormalTextView(text = project.projectName)

            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.4F)
                    .fillMaxHeight()
                    .shadow(
                        elevation = 1.dp,
                        shape = viewShape,
                        clip = false,
                        ambientColor = focusedColor,
                        spotColor = focusedColor
                    ),
                shape = viewShape,
                color = focusedColor
            )
            {
                IconButton(
                    onClick = {
                        navController.navigate("project/${project.projectId}")
                    },
                    modifier = Modifier.fillMaxSize()
                )
                {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Sağa Doğru Ok",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
