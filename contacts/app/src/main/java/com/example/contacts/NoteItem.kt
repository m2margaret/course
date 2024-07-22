package com.example.contacts

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun NoteItem(note: NoteEntity, contactId: Long, viewModel: NoteViewModel) {
    val context = LocalContext.current
    val showEditDialog = remember { mutableStateOf(false) }
    val editNoteText = remember { mutableStateOf(TextFieldValue(note.noteText)) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = note.noteText)

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { showEditDialog.value = true }) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Редактировать")
            }
            IconButton(onClick = {
                viewModel.deleteNote(note)
                Toast.makeText(context, "Заметка удалена", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Удалить")
            }
        }
    }

    // Диалог редактирования заметки
    if (showEditDialog.value) {
        AlertDialog(
            onDismissRequest = { showEditDialog.value = false },
            title = { Text("Редактировать заметку") },
            text = {
                TextField(
                    value = editNoteText,
                    onValueChange = { newValue: TextFieldValue -> editNoteText = newValue },
                    label = Text("Текст заметки"),
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    showEditDialog.value = false
                    viewModel.updateNote(NoteEntity(id = note.id, contactId = contactId, noteText = editNoteText.text))
                    editNoteText.value = TextFieldValue("")
                    Toast.makeText(context, "Заметка обновлена", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                Button(onClick = { showEditDialog.value = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}