package com.example.planningapp.view.partialview._tcs

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.view.partialview.general.textColor_beta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInputField(
    note: String,
    onNoteChange: (String) -> Unit
) {
    var showEditor by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            OutlinedTextField(
                value = note,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxSize(),
                label = { Text("Note") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit note",
                        tint = mainColor
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = outlinedTextFieldColors(
                    disabledTextColor = textColor_beta,
                    unfocusedBorderColor = textColor_beta,
                    unfocusedLabelColor = textColor_beta
                ),
                singleLine = true
            )
        }

        Box(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    detectTapGestures { showEditor = true }
                }
        )
    }

    if (showEditor) {
        FullScreenNoteEditor(
            initialText = note,
            onDismiss = { showEditor = false },
            onSave = {
                onNoteChange(it)
                showEditor = false
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenNoteEditor(
    initialText: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var tempText by remember { mutableStateOf(initialText) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Not Düzenle", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = tempText,
                    onValueChange = { if (it.length <= 250) tempText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    placeholder = { Text("Notunuzu buraya yazın...") },
                    maxLines = 20,
                    shape = RoundedCornerShape(8.dp),
                    colors = outlinedTextFieldColors(
                        focusedBorderColor = mainColor,
                        cursorColor = mainColor
                    )
                )
                Spacer(Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) { Text("İptal") }
                    Spacer(Modifier.width(8.dp))
                    TextButton(onClick = { onSave(tempText) }) { Text("Kaydet") }
                }
            }
        }
    }
}