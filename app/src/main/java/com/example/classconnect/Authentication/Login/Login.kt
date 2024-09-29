package com.example.classconnect.Authentication.Login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.classconnect.Authentication.SignUp.SignUp
import com.example.classconnect.DashBoard.Student.stdashboard
import com.example.classconnect.MainActivity // Assuming you have a MainActivity after login
import com.example.classconnect.Quiz.QuizActivity
import com.example.classconnect.R
import com.example.classconnect.chat.chat
import com.example.classconnect.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Login button click listener
        binding.loginbtn.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            // Validate input fields
            if (email.isEmpty()) {
                binding.email.error = "Email is required"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.email.error = "Please enter a valid email"
                binding.email.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.password.error = "Password is required"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.password.error = "Password must be at least 6 characters"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            // Authenticate user
            loginUser(email, password)
        }

        // Redirect to SignUp activity
        binding.signupRedirect.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in successful
                    val intent = Intent(this, stdashboard::class.java) // Navigate to the main app
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                } else {
                    // Sign-in failed, show error message
                    Toast.makeText(this, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
