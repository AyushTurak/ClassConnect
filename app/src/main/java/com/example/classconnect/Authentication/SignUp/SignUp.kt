package com.example.classconnect.Authentication.SignUp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.classconnect.Authentication.Login.Login
import com.example.classconnect.DashBoard.Student.stdashboard
import com.example.classconnect.R
import com.example.classconnect.chat.User
import com.example.classconnect.chat.chat
import com.example.classconnect.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Set up Spinner for role selection
        val roleSpinner: Spinner = findViewById(R.id.roleSpinner)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.roles_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = adapter

        // Set up sign-up button click listener
        binding.signupbtn.setOnClickListener {
            val email = binding.signupname.text.toString().trim()
            val password = binding.signuppass.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
            val selectedRole = roleSpinner.selectedItem.toString()

            // Input validation
            if (email.isEmpty()) {
                binding.signupname.error = "Email is required"
                binding.signupname.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.signupname.error = "Please provide a valid email"
                binding.signupname.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.signuppass.error = "Password is required"
                binding.signuppass.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.signuppass.error = "Password should be at least 6 characters long"
                binding.signuppass.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.confirmPasswordEditText.error = "Passwords do not match"
                binding.confirmPasswordEditText.requestFocus()
                return@setOnClickListener
            }

            // Call Firebase Auth to create user
            registerUser(email, password, selectedRole)
        }
    }

    private fun registerUser(email: String, password: String, role: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // User registered successfully
                    val name = "Anonymus"
                    mAuth =FirebaseAuth.getInstance()
                    val user = firebaseAuth.currentUser
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    // Add role to user profile or database
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(role)  // Set role in display name or save in database
                        .build()

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()

                                // Redirect to Login screen
                                val intent = Intent(this,  stdashboard::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Failed to update role.", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    // Sign-up failed, display error message
                    Toast.makeText(this, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String?, email: String, uid: String?) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid!!).setValue(User(name, email, uid))
    }
}
