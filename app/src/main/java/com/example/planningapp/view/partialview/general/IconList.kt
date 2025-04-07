package com.example.planningapp.view.partialview.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.planningapp.ui.theme.backgroundColor
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.ui.theme.mainColor

data class IconItem(
    val imageVector: ImageVector,
    val contentDescription: String,
    val destination: String
)

@Composable
fun IconList(
    navController: NavHostController,
    index: Int,
    ) {

    val icons = listOf(
        IconItem(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Check Circle",
            destination = "project"
        ),
        IconItem(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Date Range",
            destination = "home"
        ),
        IconItem(
            imageVector = Icons.Default.Check,
            contentDescription = "Build",
            destination = "check"
        )
    )

    var selectedIndex by remember { mutableIntStateOf(index) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.08F)
            .background(backgroundColor),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        itemsIndexed(icons) { index, iconItem ->

            IconItem(
                index = index,
                iconItem = iconItem,
                selectedIndex = selectedIndex,
                onItemClick = { selectedIndex = index },
                onNavigation = { navController.navigate(iconItem.destination) }
            )
        }
    }
}

@Composable
fun IconItem(
    index: Int,
    iconItem: IconItem,
    selectedIndex: Int,
    onNavigation : () -> Unit,
    onItemClick: () -> Unit
)
{
    IconButton(
        onClick = {},
        modifier = Modifier.size(40.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    onNavigation()
                    onItemClick() }
            ,
            imageVector = iconItem.imageVector,
            contentDescription = iconItem.contentDescription,
            tint = if (selectedIndex == index) focusedColor else mainColor
        )
    }
}