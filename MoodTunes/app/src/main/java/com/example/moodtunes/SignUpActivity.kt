package com.example.moodtunes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        val username = findViewById<EditText>(R.id.editTextUsername)
        val emailaddress = findViewById<EditText>(R.id.editTextEmailAddress)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val startButton = findViewById<Button>(R.id.signupButton)

        startButton.setOnClickListener {

            // convert text to string
            val userName = username.text.toString()
            val userEmail = emailaddress.text.toString()
            val userPass = password.text.toString()

            // Show a pop-up warning if a field is missing
            if (userName.isEmpty() || userEmail.isEmpty() || userPass.isEmpty()) {
                Toast.makeText(this, "Please fill in field(s)", Toast.LENGTH_LONG).show()
            }
            // Check if the password is too short (must be 10 characters or more)
            else if (userPass.length <10) {
                // Show a pop-up message to tell the user their password needs more characters
                Toast.makeText(this, "Password must be at least 10 characters", Toast.LENGTH_LONG).show()
            } else {
                val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                val editor = sharedPref.edit()

                editor.putString("name", userName)
                editor.putString("email", userEmail)
                editor.putString("pass", userPass)
                editor.apply() // Saving the data

                // Show a pop-up short message for successful sign up
                Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()

                // move to main screen
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }

        }

    }
}