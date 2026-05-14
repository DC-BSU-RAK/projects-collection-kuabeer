package com.example.moodlab

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        val videoView = findViewById<VideoView>(R.id.logoVideoView)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.logo
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        videoView.start() // Start the video playback

        videoView.setOnCompletionListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}