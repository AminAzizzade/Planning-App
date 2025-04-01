package com.example.planningapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.planningapp.view.viewmodel.DailyPlanningViewModel
import java.time.LocalDate
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import com.example.planningapp.ui.theme.backgroundColor
import com.example.planningapp.view.partialview.general.IconList

@Composable
fun CombinedScreen(
    viewModel: DailyPlanningViewModel,
    navController: NavHostController
) {
    var isCalendarScreenVisible by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    BackHandler(enabled = !isCalendarScreenVisible) {
        isCalendarScreenVisible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        IconList(navController = navController)

        if (isCalendarScreenVisible) {
            CalendarScreen(
                onDayClick = { date ->
                    selectedDate = date
                    isCalendarScreenVisible = false
                },
                selectedDate = selectedDate
            )
        } else {
            DailyPlanningScreen(
                viewModel = viewModel,
                date = selectedDate.toString(),
                onTaskClick = { navController.navigate("taskDetail/$it") },
                navController = navController
            )
        }
    }
}

