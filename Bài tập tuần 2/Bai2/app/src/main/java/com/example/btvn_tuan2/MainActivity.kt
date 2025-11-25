package com.example.btvn_tuan2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val editTextTextEmailAddress = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val textError = findViewById<TextView>(R.id.textError)
        val buttonCheck = findViewById<Button>(R.id.ButtonCheck)
        buttonCheck.setOnClickListener {
            val email = editTextTextEmailAddress.text.toString()
            if (email.isEmpty()) {
                textError.text = "Email không được để trống"
                textError.visibility = View.VISIBLE
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                textError.text = "Email không hợp lệ"
                textError.visibility = View.VISIBLE
            } else {
                textError.visibility = View.GONE
                Toast.makeText(this, "Email hợp lệ", Toast.LENGTH_SHORT).show()
            }
        }
    }

}