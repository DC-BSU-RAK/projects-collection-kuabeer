package com.example.moodtunes
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
class InstructionFragment : Fragment() {
    private lateinit var welcome: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_instruction, container, false)

        welcome = view.findViewById(R.id.welcome)

        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE)

        val text = sharedPreferences.getString("name", "")

        welcome.text = "Welcome to MoodTunes, \n$text"

        return view

    }
}