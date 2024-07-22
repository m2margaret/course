package com.example.contacts

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContactItem(contact: Contact, notes: List<NoteEntity>, viewModel: NoteViewModel) {
    val context = LocalContext.current
    val showNotesDialog = remember { mutableStateOf(false) }
    val showAddNoteDialog = remember { mutableStateOf(false) }
    val noteText = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                showNotesDialog.value = true
            }
    ) {
        Text(text = contact.name, fontSize = 18.sp)

        if (notes.isNotEmpty()) {
            Text(text = "(${notes.size} заметок)",color = Color.Gray)
        }
    }

    // Диалог с заметками
    if (showNotesDialog.value) {
        AlertDialog(
            onDismissRequest = { showNotesDialog.value = false },
            title = { Text("Заметки для ${contact.name}") },
            text = {
                Column {
                    notes.forEach { note ->
                        NoteItem(note, contact.id, viewModel)
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showNotesDialog.value = false }) {
                    Text("Закрыть")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showNotesDialog.value = false
                    showAddNoteDialog.value = true
                }) {
                    Text("Добавить заметку")
                }
            }
        )
    }

    // Диалог добавления заметки
    if (showAddNoteDialog.value) {
        AlertDialog(
            onDismissRequest = { showAddNoteDialog.value = false },
            title = { Text("Добавить заметку") },
            text = {
                TextField(
                    value = noteText,
                    onValueChange = { newValue: TextFieldValue -> noteText.value = newValue },
                    label = Text("Текст заметки"),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    showAddNoteDialog.value = false
                    viewModel.insertNote(contact.id, noteText.value.text)
                    noteText.value = TextFieldValue("")
                    Toast.makeText(context, "Заметка добавлена", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                Button(onClick = { showAddNoteDialog.value = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}