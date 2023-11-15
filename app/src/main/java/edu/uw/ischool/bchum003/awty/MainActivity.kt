package edu.uw.ischool.bchum003.awty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var message: EditText
    lateinit var phoneNumber: EditText
    lateinit var nagTime: EditText
    lateinit var startBtn: Button
    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.editMessage)
        phoneNumber = findViewById(R.id.editPhoneNumber)
        nagTime = findViewById(R.id.editNagTime)
        startBtn = findViewById<Button?>(R.id.btnStart)

        message.addTextChangedListener(watcher)
        phoneNumber.addTextChangedListener(watcher)
        nagTime.addTextChangedListener(watcher)

        // Check if the service is running
        //isServiceRunning = isServiceRunning(MessageService::class.java)

        // Update the button text based on the service state
        updateButtonText()

        // Set click listener for the "Start" button
        //startBtn.setOnClickListener {
        //    if (isServiceRunning) {
                // Stop the service
        //        stopService(Intent(this, MessageService::class.java))
        //    } else {
                // Start the service
        //        val interval = nagTime.text.toString().toLong()
        //        startService(MessageService.newIntent(this, interval))
        //    }

            // Toggle the service state
        //    isServiceRunning = !isServiceRunning

            // Update the button text
        //    updateButtonText()
        //}
        val nagTimeInterval = nagTime.text.toString()

        startBtn.setOnClickListener {
            if (startBtn.text == "Start") {
                startBtn.text = "Stop"
                //startBackgroundTask(nagTimeInterval.toLong())
                Toast.makeText(this, "${phoneNumber}:Are we there yet?", Toast.LENGTH_SHORT).show()
            } else {
                startBtn.text = "Start"
            }
        }
    }

    private fun startBackgroundTask(interval: Long) {
        Thread {
            while (true) {
                TimeUnit.MINUTES.sleep(interval)

                //val phoneNumber = "(425) 555-1212"
                //val messageText = "Are we there yet?"

                val toastMessage = "${phoneNumber}: ${message}"
                runOnUiThread {
                    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            // Check if all EditText fields are filled out with legitimate values
            val isValidInput = isNotEmpty(message) &&
                    isNotEmpty(phoneNumber) &&
                    isNotEmpty(nagTime) &&
                    isValidMinutes(nagTime)

            startBtn.isEnabled = isValidInput
        }
    }

    private fun isNotEmpty(editText: EditText): Boolean {
        return editText.text.toString().isNotEmpty()
    }

    // Check if the input in the 'Minutes Between Nag' field is a valid positive integer
    private fun isValidMinutes(editText: EditText): Boolean {
        val minutes = editText.text.toString().toLongOrNull()
        return minutes != null && minutes > 0
    }

    // Update the "Start" button
    private fun updateButtonText() {
        startBtn.text = if (isServiceRunning) "Stop" else "Start"
    }

    // Check if a service is currently running
    // private fun isServiceRunning(serviceClass: Class<*>): Boolean {
    //    val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
    //    for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
    //        if (serviceClass.name == service.service.className) {
    //            return true
    //        }
    //    }
    //    return false
    //}
}