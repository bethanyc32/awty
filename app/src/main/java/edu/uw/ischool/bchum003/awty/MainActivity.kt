package edu.uw.ischool.bchum003.awty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.util.Log

class MainActivity : AppCompatActivity() {
    lateinit var message: EditText
    lateinit var phoneNumber: EditText
    lateinit var nagTime: EditText
    lateinit var startBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.editMessage)
        phoneNumber = findViewById(R.id.editPhoneNumber)
        nagTime = findViewById(R.id.editNagTime)
        startBtn = findViewById<Button?>(R.id.btnStart)


        startBtn.setOnClickListener {
            if (startBtn.text == "Start") {
                startBtn.text = "Stop"
                Toast.makeText(this, "${phoneNumber}:Are we there yet?", Toast.LENGTH_SHORT).show()
            } else {
                startBtn.text = "Start"
            }
        }

    }
}