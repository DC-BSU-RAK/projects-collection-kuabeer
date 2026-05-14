package com.example.moodtunes

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Play the logo animation for 5 seconds, then automatically move to the sign-up screen
        Handler(Looper.getMainLooper()).postDelayed({
           val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)

    }
}