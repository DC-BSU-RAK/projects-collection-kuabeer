package com.example.moodtunes

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null

    // UI elements for music progress
    private lateinit var seekBar: SeekBar
    private lateinit var textCurrentTime: TextView
    private lateinit var textTotalTime: TextView

    // Music control buttons
    private lateinit var buttonPlay: ImageButton
    private lateinit var buttonPause: ImageButton
    private lateinit var buttonStop: ImageButton

    // Main background container for color changes
    private lateinit var mainFrame: FrameLayout

    // Handler and Runnable to update the SeekBar in real-time (every 1 second)
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var updateSeekBar: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Linking variables to XML IDs
        mainFrame = view.findViewById(R.id.homeMainFrame)
        val albumCover = view.findViewById<ImageView>(R.id.albumCover)
        val songTitle = view.findViewById<TextView>(R.id.songTitle)

        seekBar = view.findViewById(R.id.seekBar)
        textCurrentTime = view.findViewById(R.id.textCurrentTime)
        textTotalTime = view.findViewById(R.id.textTotalTime)

        buttonPlay = view.findViewById(R.id.playButton)
        buttonPause = view.findViewById(R.id.pauseButton)
        buttonStop = view.findViewById(R.id.stopButton)

        fun changeMood(color: String,cdImage: Int, name: String, song: Int) {
            // Update background color using Color.parseColor
            mainFrame.setBackgroundColor(Color.parseColor(color))
            albumCover.setImageResource(cdImage)
            // Update the display text for the song title
            songTitle.text = "Now Playing: $name"
            // Start the music transition
            startNewSong(song)
        }


        // Mood Buttons
        view.findViewById<Button>(R.id.moodHappy).setOnClickListener {
            changeMood( "#FFF9C4",R.drawable.cd_happy, "Sunny Vibes", R.raw.happy_song)
        }

        view.findViewById<Button>(R.id.moodChill).setOnClickListener {
            changeMood( "#F3E5F5",R.drawable.cd_chill, "Dreamy Flow", R.raw.chill_song)
        }

        view.findViewById<Button>(R.id.moodFun).setOnClickListener {
            changeMood( "#FCE4EC",R.drawable.cd_fun, "Party Energy", R.raw.fun_song)
        }

        view.findViewById<Button>(R.id.moodStudy).setOnClickListener {
            changeMood( "#E0F2F1",R.drawable.cd_study, "Deep Focus", R.raw.study_song)
        }

        view.findViewById<Button>(R.id.moodCalm).setOnClickListener {
            changeMood( "#E1F5FE",R.drawable.cd_calm, "Serene Soul", R.raw.calm_song)
        }

        view.findViewById<Button>(R.id.moodSad).setOnClickListener {
            changeMood( "#F5F5F5",R.drawable.cd_sad, "Rain Window", R.raw.sad_song)
        }


        // Music player control
        buttonPlay.setOnClickListener {
            mediaPlayer?.start()
            handler.post(updateSeekBar)
        }

        // Define what the updater does every second
        updateSeekBar = object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        seekBar.progress = it.currentPosition
                        textCurrentTime.text = formatTime(it.currentPosition)
                        handler.postDelayed(this, 1000)
                    }
                }
            }
        }

        buttonPause.setOnClickListener { mediaPlayer?.pause() }

        buttonStop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.prepare() // Resets song to start
            seekBar.progress = 0
            textCurrentTime.text = "0:00"
        }


        // SeekBar manual seeking (user dragging the bar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                // If the change came from the user's finger, seek the music to that spot
                if (fromUser) mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })

        return view
    }


        // Function to handle song switching
        private fun startNewSong(songRes: Int) {
            mediaPlayer?.stop()
            mediaPlayer?.release()

            mediaPlayer = MediaPlayer.create(context, songRes)
            mediaPlayer?.start()

            // Set SeekBar bounds based on the specific song duration
            mediaPlayer?.let {
                seekBar.max = it.duration
                textTotalTime.text = formatTime(it.duration)
                handler.post(updateSeekBar)
            }
        }


        // Formats milliseconds to "0:00" style
        private fun formatTime(ms: Int): String {
            val min = TimeUnit.MILLISECONDS.toMinutes(ms.toLong())
            val sec = TimeUnit.MILLISECONDS.toSeconds(ms.toLong()) % 60
            return String.format("%d:%02d", min, sec)
        }


        // Prevents app from crashing or playing music after it's closed
        override fun onDestroy() {
            super.onDestroy()
            handler.removeCallbacks(updateSeekBar)
            mediaPlayer?.release()
            mediaPlayer = null
        }

}