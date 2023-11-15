package edu.uw.ischool.bchum003.awty

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import android.app.Service
import android.os.IBinder
import java.util.concurrent.TimeUnit

class MessageService : Service()  {
    private val handler = Handler(Looper.getMainLooper())

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val interval = intent.getLongExtra("interval", 0)

            // Perform your background task here
            startBackgroundTask(interval)
        }

        // Restart the service if it gets terminated
        return START_STICKY
    }

    private fun startBackgroundTask(interval: Long) {
        Thread {
            while (true) {
                TimeUnit.MINUTES.sleep(interval)

                val phoneNumber = "(425) 555-1212" // Replace with the actual phone number
                val messageText = "Are we there yet?" // Replace with the actual message

                val message = "$phoneNumber: $messageText"

                showMessage(message)
            }
        }.start()
    }

    private fun showMessage(message: String) {
        // Perform UI-related tasks on the main thread
        runOnUiThread {
            showToast(message)
        }
    }

    private fun showToast(message: String) {
        // Show the Toast
        // Note: You can also use other UI components or mechanisms for background tasks
        // such as WorkManager, foreground service, etc., depending on your requirements
        android.widget.Toast.makeText(applicationContext, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    private fun runOnUiThread(action: () -> Unit) {
        // Ensure that the action is executed on the main (UI) thread
        handler.post(action)
    }

    companion object {
        fun newIntent(mainActivity: MainActivity, interval: Long): Intent? {
            TODO("Not yet implemented")
        }
    }
}

