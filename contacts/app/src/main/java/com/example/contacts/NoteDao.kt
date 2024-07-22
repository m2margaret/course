package com.example.contacts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("SELECT * FROM notes WHERE contactId = :contactId")
    fun getNotesByContactId(contactId: Long): Flow<List<NoteEntity>>

    @Query("DELETE FROM notes WHERE contactId = :contactId")
    suspend fun deleteNotesByContactId(contactId: Long)
}