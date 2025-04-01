package com.example.planningapp.view.partialview.general

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.planningapp.ui.theme.focusedColor
import com.example.planningapp.ui.theme.fontFamily
import com.example.planningapp.ui.theme.textColor
import com.example.planningapp.ui.theme.unfocusedColor

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
fun HoursTextView(time: String)
{
    Text(
        text = time,
        color = unfocusedColor,
        fontSize = 12.sp,
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
fun NormalTextView(text: String, fontWeight: FontWeight)
{
    Text(
        text = text,
        color = Color.White,
        fontSize = 24.sp,
        fontFamily = fontFamily,
        fontWeight = fontWeight
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
fun NormalTextView(
    text: String,
    color: Color = textColor,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.W500
)
{
    Text(
        modifier = modifier,
        text = text,
        color = color,
        fontSize = fontSize,
        fontFamily = fontFamily,
        fontWeight = fontWeight
    )
}