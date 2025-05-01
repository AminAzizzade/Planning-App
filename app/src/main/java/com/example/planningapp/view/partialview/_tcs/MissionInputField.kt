package com.example.planningapp.view.partialview._tcs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.mainColorOpacity
import com.example.planningapp.view.partialview.general.textColor_beta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionInput(
    onAddMission: (String) -> Unit
)
{
    var missionName by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    var isError   by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = missionName,

        onValueChange = {
            missionName = it
            if (it.isNotBlank()) isError = false
        },

        modifier = Modifier
            .fillMaxWidth(0.8F)
            .padding(horizontal = 32.dp)
            .onFocusChanged { isFocused = it.isFocused },

        label = { Text("Görev Adı") },
        isError = isError,
        supportingText = {

            if (isError)
            {
                Text(
                    text = "Görev adı boş olamaz!",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

        },
        trailingIcon = {
            IconButton(onClick = {
                if (missionName.isBlank()) {
                    isError = true
                } else {
                    onAddMission(missionName)
                    isError = false
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ekle",
                    tint = when {
                        isError   -> Color.Red
                        isFocused -> mainColor
                        else      -> textColor_beta
                    }
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = outlinedTextFieldColors(
            focusedBorderColor       = if (isError) Color.Red else mainColor,
            unfocusedBorderColor     = if (isError) Color.Red else textColor_beta,
            errorBorderColor         = Color.Red,
            focusedLabelColor        = if (isError) Color.Red else mainColor,
            unfocusedLabelColor      = if (isError) Color.Red else textColor_beta,
            errorLabelColor          = Color.Red,
            cursorColor              = mainColor,
            focusedTrailingIconColor = if (isError) Color.Red else mainColor,
            unfocusedTrailingIconColor = if (isError) Color.Red else textColor_beta,
            errorTrailingIconColor   = Color.Red
        ),
        singleLine = true,
    )


}