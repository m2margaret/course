package com.example.contacts

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(context: Context) : ViewModel() {

    private val noteDao = NoteDatabase.getInstance(context).noteDao()
    private val contentResolver = context.contentResolver

    init {
        contentResolver.registerContentObserver(
            ContactsContract.Contacts.CONTENT_URI,
            true,
            ContactObserver(Handler(Looper.getMainLooper()))
        )
    }

    private inner class ContactObserver(handler: Handler) : ContentObserver(handler) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)
            if (uri == ContactsContract.Contacts.CONTENT_URI) {
                handleContactDeletion(uri)
            }
        }
    }

    private fun handleContactDeletion(uri: Uri?) {
        if (uri == null) return

        val contactId = uri.lastPathSegment?.toLongOrNull() ?: return

        viewModelScope.launch {
            noteDao.deleteNotesByContactId(contactId)
        }
    }

    fun getNotesByContactId(contactId: Long) = noteDao.getNotesByContactId(contactId)

    fun insertNote(contactId: Long, noteText: String) {
        viewModelScope.launch {
            noteDao.insertNote(NoteEntity(contactId = contactId, noteText = noteText))
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            noteDao.deleteNote(note)
        }
    }
}