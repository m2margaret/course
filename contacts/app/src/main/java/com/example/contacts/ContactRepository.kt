package com.example.contacts

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ContactRepository(private val context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver

    fun getContacts(): Flow<List<Contact>> = flow {
        val cursor = queryContacts()
        val contacts = mutableListOf<Contact>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val contactId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contacts.add(Contact(contactId, name))
            }
            cursor.close()
        }
        emit(contacts)
    }

    private fun queryContacts(): Cursor? {
        return contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
    }
}