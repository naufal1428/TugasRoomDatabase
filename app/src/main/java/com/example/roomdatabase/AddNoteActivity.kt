package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.roomdatabase.Constants.NOTE_DATABASE
import com.example.roomdatabase.databinding.ActivityAddNoteBinding
import com.google.android.material.snackbar.Snackbar

class AddNoteActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddNoteBinding

    // membuat objek database untuk mengakses metode
    private val noteDB : NoteDatabase by lazy {
        Room.databaseBuilder(this,NoteDatabase::class.java,NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var noteEntity: NoteEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSave.setOnClickListener {
                val title = edtTitle.text.toString()
                val desc = edtDesc.text.toString()

                if (title.isNotEmpty() || desc.isNotEmpty()){
                    noteEntity= NoteEntity(0,title,desc)
                    noteDB.doa().insertNote(noteEntity)
                    finish()
                }
                else{
                    Snackbar.make(it,"Title and Description cannot be Empty",Snackbar.LENGTH_LONG).show()
                }
            }
        }

    }
}