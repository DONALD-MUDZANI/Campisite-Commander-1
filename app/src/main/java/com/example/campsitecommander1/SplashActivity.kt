package com.example.campsitecommander1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    // using this tag so I can find these logs easily in Logcat
    private val tag = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        Log.d(tag, "Splash screen loaded, 3 second timer is running")

        // this waits 3 seconds then automatically opens the main screen
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d(tag, "3 seconds done - opening MainActivity now")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // calling finish() so the user cant press back and land on the splash again
            finish()
        }, 3000)
    }
}
