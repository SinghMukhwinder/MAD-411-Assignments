package com.zybooks.assignment06

import android.os.Bundle
import android.view.View
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

    }

     fun showName(view: View) {
        val userName = typeUserEditText.text.toString().trim()
         displayTextView.text = "Hello, $userName!"

    }
}
