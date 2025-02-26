package com.example.planningapp

import com.example.planningapp.view.CalendarScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planningapp.ui.theme.PlanningAppTheme
import com.example.planningapp.view.DailyPlanningScreen
import com.example.planningapp.view.ProjectScreen
import com.example.planningapp.view.TaskContentScreen
import com.example.planningapp.view.viewmodel.ContentOfTaskViewModel
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel
import com.example.planningapp.view.viewmodel.ProjectViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dailyPlanningViewModel: DailyPlanningViewModel by viewModels ()
    private val contentOfTaskViewModel: ContentOfTaskViewModel by viewModels ()
    private val projectViewModel: ProjectViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanningAppTheme {
                App(
                    dailyPlanningViewModel,
                    contentOfTaskViewModel,
                    projectViewModel
                )
            }
        }
    }

    // TODO 1 : */ PopUpScreen' leri aynı yere taşımak gerek, bu sayede live performans elde edilir

    @Composable
    fun App(
        dailyPlanningViewModel: DailyPlanningViewModel,
        contentOfTaskViewModel: ContentOfTaskViewModel,
        projectViewModel: ProjectViewModel
    )
    {
        val navController = rememberNavController()
        NavHost(navController = navController,
            startDestination = //"calendar"
        "project"
        ) {
            composable("calendar") {
                CalendarScreen( onDayClick = { date ->
                    navController.navigate("details/${date}")
                })
            }
            composable("details/{date}") { backStackEntry ->
                val date = backStackEntry.arguments?.getString("date")
                DailyPlanningScreen(
                    viewModel = dailyPlanningViewModel,
                    date = date,
                    onTaskClick = { taskId ->
                        navController.navigate("content/${taskId}")
                    }
                )
            }
            composable("content/{taskId}") {
                val taskId = it.arguments?.getString("taskId")?.toInt()
                if (taskId != null) {
                    TaskContentScreen(viewModel = contentOfTaskViewModel, taskId = taskId)
                }
            }
            composable("project")
            {
                ProjectScreen(viewModel = projectViewModel)
            }
        }

    }
}
