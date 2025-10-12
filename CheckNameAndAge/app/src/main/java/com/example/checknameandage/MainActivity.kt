package com.example.checknameandage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        val EditTextName = findViewById<EditText>(R.id.editTextName)
        val EditTextAge = findViewById<EditText>(R.id.editTextAge)
        val ButtonCheck = findViewById<Button>(R.id.buttonCheck)
        val TextViewResult = findViewById<TextView>(R.id.textViewResult)


        ButtonCheck.setOnClickListener {
            val name = EditTextName.text.toString()
            val ageText = EditTextAge.text.toString()
            if (name.isEmpty() || ageText.isEmpty()) {
                TextViewResult.text = "Điền đầy đủ tên và tuổi chưa ? Điền lại đi."
                return@setOnClickListener
            }
            val age = ageText.toIntOrNull()
            if (age == null) {
                TextViewResult.text = "Nhập đúng số chưa ? Nhập lại đi."
                return@setOnClickListener
            }
            if (age < 2) {
                TextViewResult.text = "Em Bé"
            } else if (age in 2..6) {
                TextViewResult.text = "Trẻ em"
            }else if (age in 6..65) {
                TextViewResult.text = "Người lớn"
            } else {
                TextViewResult.text = "Người già"
            }
        }
    }
}