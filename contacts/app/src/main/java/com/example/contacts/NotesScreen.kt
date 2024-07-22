package com.example.contacts

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity

@Composable
fun NotesScreen(viewModel: NoteViewModel) {
    val context = LocalContext.current
    val contacts = remember { mutableStateListOf<Contact>() }
    val notes = remember { mutableStateListOf<NoteEntity>() }

    // Загрузка контактов
    LaunchedEffect(Unit) {
        val contactRepository = ContactRepository(context)
        contactRepository.getContacts().collect { contacts.addAll(it) }
    }

    // Загрузка заметок
    LaunchedEffect(contacts) {
        contacts.forEach { contact ->
            viewModel.getNotesByContactId(contact.id).collect { notes.addAll(it) }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val intent = Intent(context, AddNoteActivity::class.java)
                intent.putExtra("contactId", contacts.firstOrNull()?.id)
                startActivity(intent)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Добавить заметку")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(contacts.size) { index ->
                    ContactItem(contacts[index], notes.filter { note: NoteEntity -> note.contactId == contacts[index].id }, viewModel)
                }
            }
        }
    }
}
