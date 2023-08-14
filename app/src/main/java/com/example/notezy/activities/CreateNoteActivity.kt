package com.example.notezy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.notezy.R
import com.example.notezy.databinding.ActivityCreateNoteBinding
import com.example.notezy.models.Notes
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSaveNote.setOnClickListener {

            val noteTitle  = binding.etTitle.text.toString()
            val noteBody = binding.etBody.text.toString()

            if(noteTitle.isEmpty()){
                Toast.makeText(this, "Enter a Title", Toast.LENGTH_SHORT).show()
            }
            else if (noteBody.isEmpty()){
                Toast.makeText(this,"Enter your Note !", Toast.LENGTH_SHORT).show()
            }
            else{
                val user = intent.getStringExtra("userNext").toString()


                dbRef = FirebaseDatabase.getInstance().getReference("Notezy")

                val noteId = dbRef.child(user).push().key!!
                val note = Notes(noteId,noteTitle,noteBody)

                dbRef.child(user).child(noteId).setValue(note)
                    .addOnSuccessListener {
                        Toast.makeText(this,"Note Saved Successfully !",Toast.LENGTH_SHORT).show()

                        binding.etTitle.text.clear()
                        binding.etBody.text.clear()
                            finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"Err : ${it.message}",Toast.LENGTH_SHORT).show()
                    }

            }
        }
    }
}