package com.example.notezy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notezy.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {

            val etEmail = binding.etEmail.text.toString()
            val etPass = binding.etPassword.text.toString()
            val etConfPass = binding.etConfirmPassword.text.toString()

            if (etEmail.isEmpty()) binding.etEmail.error = "Please enter your Email"
            else if (etPass.isEmpty()) binding.etPassword.error = "Please enter your Password"
            else if (etConfPass.isEmpty()) binding.etConfirmPassword.error =
                "Please re-enter your Password"
            else if (etPass != etConfPass) {
                Toast.makeText(this, "Passwords are not matching !", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(etEmail, etPass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Successfully Registered !", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, SignInActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Err : ${it.exception}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        binding.doneSignUp.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
    }
}