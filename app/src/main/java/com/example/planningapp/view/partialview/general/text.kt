package com.example.planningapp.view.partialview.general

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.ui.theme.fontFamily
import com.example.planningapp.ui.theme.textColor

@Composable
fun TimeTextView(time: String)
{
    Text(
        text = time,
        color = focusedColor,
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Monospace,
    )
}

@Composable
fun ContainerTextView(text: String)
{
    Text(
        text = text,
        color = textColor,
        fontSize = 24.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500
    )
}


@Composable
fun NormalTextView(text: String)
{
    Text(
        text = text,
        color = Color.White,
        fontSize = 24.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500
    )
}

@Composable
fun NormalTextView(text: String, color: Color)
{
    Text(
        text = text,
        color = color,
        fontSize = 24.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500
    )
}

@Composable
fun NormalTextView(text: String, color: Color, modifier: Modifier, fontSize: Int)
{
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = fontSize.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.W500
    )
}