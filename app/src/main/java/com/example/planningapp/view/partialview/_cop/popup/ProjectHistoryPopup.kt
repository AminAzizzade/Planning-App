package com.example.planningapp.view.partialview._cop.popup

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.example.planningapp.data.entity.project.HistoryLabel
import com.example.planningapp.data.entity.project.ProjectHistory
import com.example.planningapp.view.viewmodel.ContentOfProjectViewModel

@Composable
fun ProjectHistoryPopup(
    projectId: Int, // Hangi projeye ekleneceğini belirlemek için
    viewModel: ContentOfProjectViewModel,
    onDismiss: () -> Unit,
    onHistoryAdded: () -> Int
) {
    var historyName by remember { mutableStateOf("") }
    var selectedLabel by remember { mutableStateOf(HistoryLabel.MODERATE) } // Varsayılan değer
    val focusManager = LocalFocusManager.current

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Yeni Geçmiş Ekle", style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(16.dp))

                // Geçmiş Adı Girişi
                BasicTextField(
                    value = historyName,
                    onValueChange = { historyName = it },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Label Seçimi için Dropdown Menü
                var expanded by remember { mutableStateOf(false) }
                Box {
                    Button(onClick = {
                        expanded = true
                        onHistoryAdded()
                    }) {
                        Text(selectedLabel.name)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    )
                    {
                        listOf(HistoryLabel.BAD, HistoryLabel.MODERATE, HistoryLabel.GREAT).forEach { label ->
                            DropdownMenuItem(
                                text = { Text(label.name) },
                                onClick = {
                                    selectedLabel = label
                                    expanded = false
                                }
                            )
                        }
                    }
                }

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
                            if (historyName.isNotBlank()) {
                                val newHistory = ProjectHistory(
                                    projectHistoryId = 0, // Auto-generate olduğu için 0 veriyoruz
                                    projectId = projectId,
                                    projectHistoryName = historyName,
                                    historyLabel = selectedLabel
                                )
                                viewModel.insertProjectHistory(newHistory)

                                onHistoryAdded()
                                onDismiss()
                            }
                        },
                        enabled = historyName.isNotBlank()
                    ) {
                        Text("Kaydet")
                    }
                }
            }
        }
    }
}