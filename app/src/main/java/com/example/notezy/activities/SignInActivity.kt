package com.example.notezy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notezy.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSignIn.setOnClickListener {

            val etEmail = binding.etEmail.text.toString()
            val etPass = binding.etPassword.text.toString()

            if (etEmail.isEmpty()) binding.etEmail.error = "Please enter your Email"
            else if (etPass.isEmpty()) binding.etPassword.error = "Should not be Empty !"
            else {

                auth.signInWithEmailAndPassword(etEmail, etPass)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Successfully Signed In !", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        val user = FirebaseAuth.getInstance().uid.toString()

                        intent.putExtra("user",user)
                        startActivity(intent)

                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Err : ${it.message.toString()}", Toast.LENGTH_LONG)
                            .show()
                    }
            }
        }

        binding.tvNotSignIn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }
}