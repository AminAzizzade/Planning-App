package com.example.planningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.planningapp.ui.theme.PlanningAppTheme
import com.example.planningapp.view.CalendarScreen
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


//                var selectedDay by remember { mutableIntStateOf(5) }
//                DaySelector(
//                    selectedDay = selectedDay,
//                    lastDayOfMonth = 31,
//                    onDaySelected = { newDay -> selectedDay = newDay }
//                )


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



@Composable
fun DaySelector(
    lastDayOfMonth: Int,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    val days = remember(selectedDay) {
        List(5) { index ->
            val calculatedDay = selectedDay - 2 + index
            when {
                calculatedDay > lastDayOfMonth -> 0 // Ayın son gününden sonraki günler için 0
                else -> calculatedDay
            }
        }
    }


    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF4285F4))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(days) { day ->
            DayItem(day, selectedDay, onDaySelected)
        }
    }
}

@Composable
fun DayItem(day: Int, selectedDay: Int, onDaySelected: (Int) -> Unit) {
    val isSelected = day == selectedDay
    val backgroundColor = if (isSelected) Color.White else Color.Transparent
    val textColor = if (isSelected) Color(0xFF4285F4) else Color.White
    val borderWidth = if (isSelected) 2.dp else 0.dp
    val borderColor = if (isSelected) Color(0xFF4285F4) else Color.Transparent

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { if (day > 0) onDaySelected(day) }
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        if (day > 0) {
            Text(
                text = day.toString(),
                color = textColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
