package com.example.moodlab

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Instruction Button
        // Find the info icon button from the main activity screen
        val instructionButton : ImageButton = findViewById(R.id.info_icon_button)

        instructionButton.setOnClickListener{
            // Create a dialog popup
            val dialog = android.app.Dialog(this)
            // Load the instruction layout design
            dialog.setContentView(R.layout.activity_instruction)
            // Make the dialog background transparent
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            // Find the close button inside the dialog layout ( instruction screen )
            val closeButton : Button = dialog.findViewById(R.id.closeButton)

            // when clicked, the dialog close
            closeButton.setOnClickListener {
                dialog.dismiss() // Closes the manual
            }
            dialog.show() // Show the manual and automatically dim the background
        }

        // list of all radio groups
        val groupIds = listOf(
            R.id.radioGroup1, R.id.radioGroup2, R.id.radioGroup3, R.id.radioGroup4,
            R.id.radioGroup5, R.id.radioGroup6, R.id.radioGroup7, R.id.radioGroup8
        )

        // list of all yes buttons
        val yesButtonIds = listOf(
            R.id.yesButton1, R.id.yesButton2, R.id.yesButton3, R.id.yesButton4,
            R.id.yesButton5, R.id.yesButton6, R.id.yesButton7, R.id.yesButton8
        )

        // Result Button
        //Find the analyze data button from the main activity screen
        val resultButton : Button = findViewById(R.id.analyzeButton)

        resultButton.setOnClickListener {
            var score = 0 // start score from 0
            var allAnswered = true // all question should be answer

            // i = index
            for (i in groupIds.indices) {
                // Find the RadioGroup on the main activity screen
                val radioGroups = findViewById<RadioGroup>(groupIds[i])

                // Check if the question was skipped
                if (radioGroups.checkedRadioButtonId == -1) {
                    allAnswered = false
                }
                // Increase score if "Yes" was selected
                if (radioGroups.checkedRadioButtonId == yesButtonIds[i]) {
                    score++
                }
            }

            if (!allAnswered) {
                // Show a quick pop-up message at the bottom if all or some question arent answer
                Toast.makeText(this, "Please answer all questions first!", Toast.LENGTH_LONG).show()

            } else {
                // Only show the results if all questions are answered!
                val resultTitle: String
                val resultMessage: String

                if (score <= 3) {
                    resultTitle = "Low Battery"
                    resultMessage =
                        "It's okay to have off days. Try to drink some water and take a 5-minute break. You matter!"
                } else if (score <= 5) {
                    resultTitle = "Getting There!"
                    resultMessage =
                        "You're doing a good job taking care of yourself. A little more rest might make you feel even better!"
                } else {
                    resultTitle = "Glowing!"
                    resultMessage =
                        "Wow! You are prioritizing your well-being. Keep up this amazing energy!"
                }

                showResultPopup(resultTitle, resultMessage,score)
            }
        }

        // Clear Button
        val clearButton: Button = findViewById(R.id.clearButton)

        clearButton.setOnClickListener {
            for (id in groupIds) {
                findViewById<RadioGroup>(id).clearCheck() // Reset every RadioGroup so the user can start again
            }
        }
    }

    private fun showResultPopup(title: String, message: String, score: Int) {
        val dialog = android.app.Dialog(this)
        dialog.setContentView(R.layout.activity_result)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val gifView = dialog.findViewById<ImageView>(R.id.gifView)

        val rootLayout = dialog.findViewById<ConstraintLayout>(R.id.resultScreen)
        val titleText = dialog.findViewById<TextView>(R.id.resultTitle)
        val messageText = dialog.findViewById<TextView>(R.id.resultMessage)
        val closeBtn = dialog.findViewById<Button>(R.id.closeButton)

        titleText.text = title
        messageText.text = message

        // background change logic
        when {

            score <= 3 -> {
                rootLayout.setBackgroundResource(R.drawable.mood_lab_pink)
                Glide.with(this).load(R.raw.sad).into(gifView)
            }

            score <= 5 -> {
                rootLayout.setBackgroundResource(R.drawable.mood_lab_blue)
                Glide.with(this).load(R.raw.confuse).into(gifView)
            }

            else -> {
                rootLayout.setBackgroundResource(R.drawable.mood_lab_green)
                Glide.with(this).load(R.raw.smile).into(gifView)
            }

        }

        closeBtn.setOnClickListener {
            dialog.dismiss() // Closes the result
        }

        dialog.show() // Show the result and automatically dim the background
    }
}
