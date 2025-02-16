package com.example.planningapp.view.partialview._tcs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planningapp.data.entity.TaskLabel
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.textColor

/**
 * Label seçim dropdown menüsü
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelDropdownMenu(
    selectedLabel: TaskLabel,
    onLabelSelected: (TaskLabel) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(TaskLabel.WORK, TaskLabel.SCHOOL, TaskLabel.HOME)
    val shape = RoundedCornerShape(16.dp)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedLabel.name,
            onValueChange = {},
            label = { Text("Label") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(12.dp),
            shape = shape,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = mainColor,
                unfocusedBorderColor = textColor
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.name) },
                    onClick = {
                        onLabelSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}