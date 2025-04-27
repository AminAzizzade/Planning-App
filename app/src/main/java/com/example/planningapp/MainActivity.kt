package com.example.planningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planningapp.ui.theme.PlanningAppTheme
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.view.CalendarScreen
import com.example.planningapp.view.CombinedScreen
import com.example.planningapp.view.ContentOfProjectScreen
import com.example.planningapp.view.DailyPlanningScreen
import com.example.planningapp.view.ProjectScreen
import com.example.planningapp.view.TaskContentScreen
import com.example.planningapp.view.partialview.general.ContainerTextView
import com.example.planningapp.view.partialview.general.textColor_beta
import com.example.planningapp.view.viewmodel.ContentOfProjectViewModel
import com.example.planningapp.view.viewmodel.ContentOfTaskViewModel
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel
import com.example.planningapp.view.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dailyPlanningViewModel: DailyPlanningViewModel by viewModels ()
    private val contentOfTaskViewModel: ContentOfTaskViewModel by viewModels ()
    private val projectViewModel: ProjectViewModel by viewModels ()
    private val contentOfProjectViewModel: ContentOfProjectViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanningAppTheme {
                App(
                    dailyPlanningViewModel,
                    contentOfTaskViewModel,
                    projectViewModel,
                    contentOfProjectViewModel,
                )
            }
        }
    }

    @Composable
    fun App(
        dailyPlanningViewModel: DailyPlanningViewModel,
        contentOfTaskViewModel: ContentOfTaskViewModel,
        projectViewModel: ProjectViewModel,
        contentOfProjectViewModel: ContentOfProjectViewModel,
    )
    {
        val navController = rememberNavController()
        NavHost(navController = navController,
            startDestination = "home"
            //"calendar"
            //"project"
        )
        {
            composable("home")
            {
                CombinedScreen(
                    viewModel = dailyPlanningViewModel,
                    navController = navController
                )
            }

            composable("calendar") {

                CalendarScreen(
                    onDayClick = { date ->
                        navController.navigate("details/${date}")
                    }
                )

            }

            composable("details/{date}") { backStackEntry ->
                val date = backStackEntry.arguments?.getString("date")

                DailyPlanningScreen(
                    navController = navController,
                    viewModel = dailyPlanningViewModel,
                    date = date,
                    onTaskClick = { taskId ->
                        navController.navigate("content/${taskId}")
                    }
                )
            }

            composable("content/{taskId}") {backStackEntry ->
                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
                val viewModel: ContentOfTaskViewModel = hiltViewModel(backStackEntry)
                if (taskId != null) {
                    TaskContentScreen(
                        navController = navController,
                        viewModel = viewModel,
                        taskId = taskId
                    )
                }
            }

            composable("project") {

                ProjectScreen(
                    navController = navController,
                    viewModel = projectViewModel
                )
            }

            composable("project/{projectId}") { backStackEntry ->
                // NavBackStackEntry'yi hiltViewModel fonksiyonuna geçiriyoruz
                val viewModel: ContentOfProjectViewModel = hiltViewModel(backStackEntry)
                val projectId = backStackEntry.arguments?.getString("projectId")?.toIntOrNull()
                if (projectId != null) {
                    ContentOfProjectScreen(
                        navController = navController,
                        viewModel = viewModel,
                        projectId = projectId
                    )
                }
            }

        }
    }
}