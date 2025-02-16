package com.example.planningapp.view.partialview._tcs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.planningapp.ui.theme.mainColor
import com.example.planningapp.ui.theme.textColor

/**
 * Note giriş alanı
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteField(note: String, onNoteChange: (String) -> Unit) {
    OutlinedTextField(
        value = note,
        onValueChange = {
            if (it.length <= 250) onNoteChange(it)
        },
        label = { Text("Note") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = mainColor,
            unfocusedBorderColor = textColor
        ),
        singleLine = false,
        maxLines = 5
    )
}
