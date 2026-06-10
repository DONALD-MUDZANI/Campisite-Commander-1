package com.example.campsitecommander1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val LOG_TAG = "SplashActivityLogs"
    private val splashHandler = Handler(Looper.getMainLooper())
    private lateinit var printableRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.d(LOG_TAG, "Splash screen is active.")

        val btnStart: Button = findViewById(R.id.btnStart)
        val btnExit: Button = findViewById(R.id.btnExit)

        printableRunnable = Runnable { navigateToMain() }
        splashHandler.postDelayed(printableRunnable, 3000L)

        btnStart.setOnClickListener {
            splashHandler.removeCallbacks(printableRunnable)
            navigateToMain()
        }

        btnExit.setOnClickListener {
            splashHandler.removeCallbacks(printableRunnable)
            finishAffinity()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}