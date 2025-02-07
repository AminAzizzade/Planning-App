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
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dailyPlanningViewModel: DailyPlanningViewModel by viewModels ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanningAppTheme {
                App(dailyPlanningViewModel)
            }
        }
    }

    @Composable
    fun App(dailyPlanningViewModel: DailyPlanningViewModel)
    {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "calendar") {
            composable("calendar") {
                CalendarScreen( onDayClick = { date ->
                    navController.navigate("details/${date}")
                })
            }
            composable("details/{date}") { backStackEntry ->
                val date = backStackEntry.arguments?.getString("date")
                DailyPlanningScreen(viewModel = dailyPlanningViewModel, date = date)
            }
        }
    }

}
