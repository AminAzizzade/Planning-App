package com.example.planningapp.view.partialview._tcs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.planningapp.data.entity.TaskLabel
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.view.partialview.general.textColor_beta


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelDropdownMenu(
    labelState: TaskLabel,
    onLabelSelected: (TaskLabel) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val options = TaskLabel.entries
    val shape = RoundedCornerShape(16.dp)

    fun TaskLabel.icon() = when (this) {
        TaskLabel.WORK -> Icons.Default.AccountBox
        TaskLabel.SCHOOL -> Icons.Default.Email
        TaskLabel.HOME -> Icons.Default.Home
    }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth(0.8f),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = labelState.name,
            onValueChange = {},
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            shape = shape,
            label = { Text("Label") },
            leadingIcon = {
                Icon(
                    imageVector = labelState.icon(),
                    contentDescription = null,
                    tint = mainColor
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = outlinedTextFieldColors(
                focusedBorderColor = mainColor,
                unfocusedBorderColor = textColor_beta,
                focusedLabelColor = mainColor,
                unfocusedLabelColor = textColor_beta
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, shape)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = option.icon(),
                                contentDescription = null,
                                tint = mainColor,
                                modifier = Modifier.size(20.dp).fillMaxSize()
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(option.name)
                        }
                    },
                    onClick = {
                        onLabelSelected(option)
                        expanded = false
                    },
                    colors = MenuItemColors(
                        textColor = MaterialTheme.colorScheme.onSurface,
                        leadingIconColor = mainColor,
                        trailingIconColor = if (option == labelState) mainColor.copy(alpha = 0.1f) else Color.Transparent,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledLeadingIconColor = mainColor,
                        disabledTrailingIconColor = mainColor
                    ),
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }
    }
}