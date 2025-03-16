package com.example.planningapp.view.partialview._cop.popup

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.planningapp.data.entity.project.ProjectTask
import com.example.planningapp.view.viewmodel.ContentOfProjectViewModel

@Composable
fun ProjectTaskPopup(
    projectId: Int, // Hangi projeye ekleneceğini belirlemek için
    viewModel: ContentOfProjectViewModel,
    onDismiss: () -> Unit,
    onTaskAdded: () -> Int
) {
    var taskName by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Yeni Görev Ekle", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(16.dp))

                // Görev Adı Girişi
                BasicTextField(
                    value = taskName,
                    onValueChange = { taskName = it },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Kaydet ve İptal Butonları
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("İptal")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (taskName.isNotBlank()) {
                                val newTask = ProjectTask(
                                    projectTaskId = 0, // Auto-generate olduğu için 0 veriyoruz
                                    projectId = projectId,
                                    taskName = taskName
                                )
                                viewModel.insertProjectTask(newTask)
                                onDismiss()
                                onTaskAdded()
                            }
                        },
                        enabled = taskName.isNotBlank()
                    ) {
                        Text("Kaydet")
                    }
                }
            }
        }
    }
}