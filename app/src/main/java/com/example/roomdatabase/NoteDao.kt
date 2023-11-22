package com.example.roomdatabase

import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.roomdatabase.Constants.NOTE_TABLE

@Dao
interface NoteDao {

    // onConflict is used for replacing old data and continue the transaction with the new data.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteEntity: NoteEntity)

    @Update
    fun updateNote(noteEntity: NoteEntity)

    @Delete
    fun deleteNote(noteEntity: NoteEntity)

    // getting all the notes and showing them to the recyclerView in the MainActivity
    @Query("SELECT * FROM $NOTE_TABLE ORDER BY noteId DESC")
    fun getAllNotes() : MutableList<NoteEntity>

    // selecting one note at a time
    @Query("SELECT * FROM $NOTE_TABLE WHERE noteId LIKE :id")
    fun getNote(id : Int) : NoteEntity

}