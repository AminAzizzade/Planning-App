package com.example.planningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planningapp.ui.theme.PlanningAppTheme
import com.example.planningapp.view.CalendarScreen
import com.example.planningapp.view.CombinedScreen
import com.example.planningapp.view.ContentOfProjectScreen
import com.example.planningapp.view.DailyPlanningScreen
import com.example.planningapp.view.ProjectScreen
import com.example.planningapp.view.TaskContentScreen
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