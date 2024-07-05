package com.example.ToDo.Database

import androidx.lifecycle.LiveData
import com.example.ToDo.Models.Note

class NotesRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes() // Update property to non-nullable LiveData

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun update(note: Note) {
        val rowsUpdated = note.id?.let { note.title?.let { it1 ->
            note.note?.let { it2 ->
                noteDao.update(it,
                    it1, it2
                )
            }
        } }
        if (rowsUpdated == 0) {
            // Handle accordingly, maybe throw an exception or log a message
        }
    }
}