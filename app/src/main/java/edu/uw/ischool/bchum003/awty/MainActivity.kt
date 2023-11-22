package edu.uw.ischool.bchum003.awty

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.text.Editable
import android.text.TextWatcher
import java.util.concurrent.TimeUnit

const val ALARM_ACTION = "edu.uw.ischool.bchum003.awty"

class MainActivity : AppCompatActivity() {
    lateinit var message: EditText
    lateinit var phoneNumber: EditText
    lateinit var nagTime: EditText
    lateinit var startBtn: Button
    // private var isServiceRunning = false

    var receiver : BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        message = findViewById(R.id.editMessage)
        phoneNumber = findViewById(R.id.editPhoneNumber)
        nagTime = findViewById(R.id.editNagTime)
        startBtn = findViewById<Button?>(R.id.btnStart)

        startBtn.setOnClickListener { nagAlarm() }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
        receiver = null
    }

    private fun nagAlarm() {
        val activityThis = this
        if (startBtn.text == "Start") {
            startBtn.text = "Stop"
        } else {
            startBtn.text = "Start"
        }

        if (receiver == null) {
            // Make sure of our BroadcastReceiver; remember how we can
            // create an object on the fly that inherits from a class?
            // Let's use that to create an anonymous subclass of the
            // BroadcastReceiver type, and then register it dynamically
            // since we don't really need much beyond just catching the
            // intent fired at us from the AlarmManager
            receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    Toast.makeText(activityThis, "${phoneNumber.text}: ${message.text}", Toast.LENGTH_SHORT).show()
                }
            }
            val filter = IntentFilter(ALARM_ACTION)
            registerReceiver(receiver, filter)
        }

        // Create the PendingIntent
        val intent = Intent(ALARM_ACTION)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Get the Alarm Manager
        val alarmManager : AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
            (nagTime.text.toString().toInt() * 1000).toLong(), pendingIntent)
    }
    //(nagTime.text.toString().toInt() * 60000)
}
