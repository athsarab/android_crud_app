package com.example.ToDo.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ToDo.Models.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>> // Update return type to non-nullable LiveData

    @Query("UPDATE notes_table SET title = :title, note = :note WHERE id = :id")
    fun update(id: Int, title: String, note: String): Int // Update parameter and return types to non-nullable
}