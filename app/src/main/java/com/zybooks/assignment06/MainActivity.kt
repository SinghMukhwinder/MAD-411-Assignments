package com.zybooks.assignment06

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var typeUserEditText: EditText
    private lateinit var showNameButton: Button
    private lateinit var displayTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        typeUserEditText = findViewById(R.id.edit_text)
        showNameButton = findViewById(R.id.show_name_button)
        displayTextView = findViewById(R.id.text_view)



        showNameButton.setOnClickListener{
            showName()
        }
    }

    private fun showName() {
        val userName = typeUserEditText.text.toString().trim()

        if (userName.isNotEmpty()) {
            displayTextView.text = "Hello, $userName!"
        } else {
            displayTextView.text = "Please enter your name."
        }
    }
}
