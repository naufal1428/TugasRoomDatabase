package com.example.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.roomdatabase.Constants.NOTE_DATABASE
import com.example.roomdatabase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    // membuat objek database
    private val noteDB : NoteDatabase by lazy {
        Room.databaseBuilder(this,NoteDatabase::class.java,NOTE_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val noteAdapter by lazy { NoteAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Notes"
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNote.setOnClickListener {
            startActivity(Intent(this,AddNoteActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem(){
        binding.apply {
            // jika database tidak kosong maka tampilkan daftar datanya
            if(noteDB.doa().getAllNotes().isNotEmpty()){
                rvNoteList.visibility= View.VISIBLE
                tvEmptyText.visibility=View.GONE
                noteAdapter.differ.submitList(noteDB.doa().getAllNotes())
                setupRecyclerView()
            }else{
                rvNoteList.visibility=View.GONE
                tvEmptyText.visibility= View.VISIBLE
            }
        }
    }

    private fun setupRecyclerView(){
        binding.rvNoteList.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=noteAdapter
        }

    }
}