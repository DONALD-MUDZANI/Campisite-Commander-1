package com.example.campsitecommander1

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    
    private val itemNames = arrayListOf("Tent", "Marshmallows", "Flashlight")
    private val itemCategories = arrayListOf("Shelter", "Food", "Safety")
    private val itemQuantities = arrayListOf(1, 3, 2)
    private val itemComments = arrayListOf(
        "4-person waterproof", "For S'mores (Mega size)", "Check batteries (AA)"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(tag, "Main screen loaded")

        val btnAddGear = findViewById<Button>(R.id.btnLaunchAddGear)
        val btnViewChecklist = findViewById<Button>(R.id.btnViewFullChecklist)
        val btnExit = findViewById<Button>(R.id.btnExitApplication)
        val tvTotal = findViewById<TextView>(R.id.tvTotalItemsCount)

        updateTotalDisplay(tvTotal)

        btnAddGear.setOnClickListener {
            Log.d(tag, "Add Gear button clicked")
            showAddGearDialog(tvTotal)
        }

        btnViewChecklist.setOnClickListener {
            Log.d(tag, "View Checklist button clicked")
            if (itemNames.isEmpty()) {
                Toast.makeText(this, "No gear logged yet. Add some items first!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, DetailActivity::class.java).apply {
                putStringArrayListExtra("KEY_NAMES", itemNames)
                putStringArrayListExtra("KEY_CATEGORIES", itemCategories)
                putIntegerArrayListExtra("KEY_QUANTITIES", itemQuantities)
                putStringArrayListExtra("KEY_COMMENTS", itemComments)
            }
            startActivity(intent)
        }

        btnExit.setOnClickListener {
            Log.d(tag, "Exit button clicked")
            finishAffinity()
        }
    }

    private fun calculateTotalPackedItems(quantities: ArrayList<Int>): Int {
        var total = 0
        for (q in quantities) {
            total += q
        }
        return total
    }

    private fun updateTotalDisplay(tv: TextView) {
        val total = calculateTotalPackedItems(itemQuantities)
        tv.text = total.toString()
    }

    private fun showAddGearDialog(tvTotal: TextView) {
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 30)
        }
        val etName = EditText(this).apply { hint = "Gear Name" }
        val etCategory = EditText(this).apply { hint = "Category" }
        val etQty = EditText(this).apply {
            hint = "Quantity"
            inputType = InputType.TYPE_CLASS_NUMBER
        }
        val etNotes = EditText(this).apply { hint = "Notes" }

        container.addView(etName)
        container.addView(etCategory)
        container.addView(etQty)
        container.addView(etNotes)

        AlertDialog.Builder(this)
            .setTitle("Add Equipment")
            .setView(container)
            .setPositiveButton("Save") { dialog, _ ->
                val name = etName.text.toString().trim()
                val category = etCategory.text.toString().trim()
                val qtyStr = etQty.text.toString().trim()
                val notes = etNotes.text.toString().trim()

                if (name.isEmpty() || category.isEmpty() || qtyStr.isEmpty()) {
                    Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }

                try {
                    val qty = qtyStr.toInt()
                    itemNames.add(name)
                    itemCategories.add(category)
                    itemQuantities.add(qty)
                    itemComments.add(if (notes.isEmpty()) "No notes" else notes)
                    updateTotalDisplay(tvTotal)
                    dialog.dismiss()
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    override fun onResume() {
        super.onResume()
        val tvTotal = findViewById<TextView>(R.id.tvTotalItemsCount)
        if (tvTotal != null) updateTotalDisplay(tvTotal)
    }
}