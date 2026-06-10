package com.example.campsitecommander1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    private val tag = "DetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        
        Log.d(tag, "Detail screen loaded")
        
        val container = findViewById<LinearLayout>(R.id.linearScrollContainer)
        val btnBack = findViewById<Button>(R.id.btnBackToBase)

        // Getting the arrays that MainActivity sent over
        val names = intent.getStringArrayListExtra("KEY_NAMES") ?: arrayListOf()
        val categories = intent.getStringArrayListExtra("KEY_CATEGORIES") ?: arrayListOf()
        val quantities = intent.getIntegerArrayListExtra("KEY_QUANTITIES") ?: arrayListOf()
        val comments = intent.getStringArrayListExtra("KEY_COMMENTS") ?: arrayListOf()

        Log.d(tag, "Received ${names.size} items, building display rows")

        for (i in names.indices) {
            val tvItem = TextView(this)
            tvItem.text = "${i + 1}. ${names[i]}\n" +
                    " Category: ${categories[i]}\n" +
                    " Quantity: ${quantities[i]}\n" +
                    " Notes: ${comments[i]}\n" +
                    "-----------------------------------"
            tvItem.textSize = 16f
            tvItem.setTextColor(android.graphics.Color.WHITE)
            tvItem.setPadding(12, 16, 12, 16)
            container.addView(tvItem)
        }

        btnBack.setOnClickListener {
            Log.d(tag, "Back to Base clicked")
            finish()
        }
    }
}