package com.example.moodtunes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val nameView = view.findViewById<TextView>(R.id.displayUserName)
        val emailView = view.findViewById<TextView>(R.id.displayUserEmail)
        val signOut = view.findViewById<Button>(R.id.signOutButton)

        val sharedPref = requireActivity().getSharedPreferences(
            "UserPrefs",
            android.content.Context.MODE_PRIVATE
        )

        // Get the data you saved in SignUpActivity
        nameView.text = sharedPref.getString("name", "User")
        emailView.text = sharedPref.getString("email", "No Email")

        signOut.setOnClickListener {
            // Clear data and go back to Sign Up
            sharedPref.edit().clear().apply()
            val intent = Intent(activity, SignUpActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return view
    }

}