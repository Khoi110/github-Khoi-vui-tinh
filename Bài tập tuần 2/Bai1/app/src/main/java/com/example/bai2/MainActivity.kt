package com.example.bai2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
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

        val EditTextNumber = findViewById<EditText>(R.id.editTextNumber)
        val ButtonCreate = findViewById<Button>(R.id.buttonCreate)
        val ErrorText = findViewById<TextView>(R.id.ErrorView)
        val container = findViewById<LinearLayout>(R.id.container)

        ButtonCreate.setOnClickListener {
            container.removeAllViews()
            ErrorText.visibility = View.GONE

            val input = EditTextNumber.text.toString()

            // Kiểm tra nhập liệu
            if (input.isEmpty() || !input.matches("\\d+".toRegex())) {
                ErrorText.text = "Dữ liệu bạn nhập không hợp lệ"
                ErrorText.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val number = input.toInt()

            // Sinh ra các button 1..number
            for (i in 1..number) {
                val btn = Button(this)
                btn.text = i.toString()

                // style button đỏ
                btn.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,   // full chiều ngang
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(10, 10, 10, 10)
                btn.layoutParams = params

                container.addView(btn)
            }
        }
    }
}
