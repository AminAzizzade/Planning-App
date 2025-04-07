package com.example.planningapp.view.partialview._ps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planningapp.ui.theme.unfocusedColor

@Composable
fun NavigateProjectButton(
    projectId: Int,
    navController: NavHostController,
    backgroundColor: Color = unfocusedColor
) {
    val shape = RoundedCornerShape(
        topStart = 8.dp,
        bottomStart = 8.dp,
        topEnd = 36.dp,
        bottomEnd = 36.dp
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth(0.4F)
            .fillMaxHeight()
            .shadow(
                elevation = 1.dp,
                shape = shape,
                clip = false,
                ambientColor = backgroundColor,
                spotColor = backgroundColor
            ),
        shape = shape,
        color = backgroundColor
    ) {
        IconButton(
            onClick = {
                navController.navigate("project/$projectId")
            },
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Sağa Doğru Ok",
                tint = Color.White
            )
        }
    }
}