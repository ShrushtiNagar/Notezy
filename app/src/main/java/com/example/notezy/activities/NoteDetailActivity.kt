package com.example.notezy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.notezy.R
import com.example.notezy.databinding.ActivityNoteDetailBinding
import com.example.notezy.models.Notes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NoteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitleDetail.text = intent.getStringExtra("noteTitle").toString()
        binding.tvBodyDetail.text = intent.getStringExtra("noteBody").toString()

        binding.btnUpdate.setOnClickListener {
            openUpdateDialog()
        }

        binding.btnDelete.setOnClickListener {

            val user = FirebaseAuth.getInstance().uid.toString()
            val dbRef = FirebaseDatabase.getInstance().getReference("Notezy").child(user)
            val noteId = intent.getStringExtra("noteId").toString()

            dbRef.child(noteId).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(this, "Note Deleted Successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "err : ${it.message}", Toast.LENGTH_SHORT).show()
                }

        }
    }

    private fun openUpdateDialog() {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)
        mDialog.setView(mDialogView)

        val noteTitle = mDialogView.findViewById<EditText>(R.id.etTitleUpdate)
        val noteBody = mDialogView.findViewById<EditText>(R.id.etBodyUpdate)
        val btnUpdate = mDialogView.findViewById<Button>(R.id.btnUpdate)

        noteTitle.setText(binding.tvTitleDetail.text.toString())
        noteBody.setText(binding.tvBodyDetail.text.toString())

        mDialog.setTitle("Updating Note..")
        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener {
            val noteId = intent.getStringExtra("noteId").toString()

            val noteInfo = Notes(noteId,
                    noteTitle.text.toString(),
                    noteBody.text.toString())

            val user = FirebaseAuth.getInstance().uid.toString()
            val dbRef = FirebaseDatabase.getInstance().getReference("Notezy").child(user)
            dbRef.child(noteId).setValue(noteInfo)

            binding.tvTitleDetail.text = noteTitle.text
            binding.tvBodyDetail.text = noteBody.text

            noteTitle.text.clear()
            noteBody.text.clear()

            alertDialog.dismiss()
        }
    }
}