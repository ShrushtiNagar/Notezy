package com.example.notezy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notezy.models.Notes
import com.example.notezy.R
import com.example.notezy.adapters.NotesAdapter
import com.example.notezy.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var notesList: MutableList<Notes>
    private lateinit var rvAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notesList = mutableListOf()
        binding.rvNotes.layoutManager = LinearLayoutManager(this)

        dbRef = FirebaseDatabase.getInstance().getReference("Notezy")

        getUserNotes()

        val user = intent.getStringExtra("user").toString()

        binding.faBtn.setOnClickListener {
            openActivity(user)
        }

    }

    private fun openActivity(user: String) {

        val intent2 = Intent(this, CreateNoteActivity::class.java)
        intent2.putExtra("userNext", user)

        startActivity(intent2)
    }

    private fun getUserNotes() {

        val user = intent.getStringExtra("user").toString()

        dbRef.child(user).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                notesList.clear()
                if (snapshot.exists()) {
                    for (note in snapshot.children) {
                        val noteInfo = note.getValue(Notes::class.java)
                        notesList.add(noteInfo!!)
                    }
                    rvAdapter = NotesAdapter(notesList)

                    rvAdapter.setItemClickListener(object : NotesAdapter.onItemClickListener {

                        override fun onClick(position: Int) {
                            val intent3 = Intent(this@MainActivity, NoteDetailActivity::class.java)

                            intent3.putExtra("noteTitle",notesList[position].title)
                            intent3.putExtra("noteBody",notesList[position].body)
                            intent3.putExtra("noteId",notesList[position].noteId)
                            startActivity(intent3)
                        }

                    })
                    binding.rvNotes.adapter = rvAdapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }
}