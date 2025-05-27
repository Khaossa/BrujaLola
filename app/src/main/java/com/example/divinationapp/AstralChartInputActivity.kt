package com.example.divinationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
// Removed CheckBox, EditText, TimePicker, Toast imports
import android.widget.DatePicker
import com.example.divinationapp.utils.ZodiacUtils // Import the utility
// Removed Calendar import as it's not used
// import java.util.Calendar

class AstralChartInputActivity : AppCompatActivity() {

    private lateinit var birthDatePicker: DatePicker
    // Removed birthTimePicker, timeUnknownCheckbox, birthLocationEditText
    private lateinit var calculatePlacementsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_astral_input)

        // Initialize UI elements
        birthDatePicker = findViewById(R.id.birth_date_picker_astral)
        // Removed findViewById for birthTimePicker, timeUnknownCheckbox, birthLocationEditText
        calculatePlacementsButton = findViewById(R.id.calculate_placements_button)

        // Removed time picker default and checkbox listener logic

        calculatePlacementsButton.setOnClickListener {
            handleCalculatePlacementsClick()
        }
    }

    private fun handleCalculatePlacementsClick() {
        val day = birthDatePicker.dayOfMonth
        val month = birthDatePicker.month // 0-indexed
        val year = birthDatePicker.year

        // Removed logic for hour, minute, isTimeKnown, and location
        // Removed location validation

        // Create Intent for AstralChartDisplayActivity
        val intent = Intent(this, AstralChartDisplayActivity::class.java).apply {
            putExtra("YEAR", year)
            putExtra("MONTH", month) // Month is 0-indexed
            putExtra("DAY", day)
            // Calculate Sun Sign using the utility
            val sunSign = ZodiacUtils.getSunSign(day, month)
            putExtra("SUN_SIGN", sunSign)
            // Removed HOUR, MINUTE, IS_TIME_KNOWN, LOCATION_STRING extras
        }
        startActivity(intent)
    }

    // Removed the local getSunSign method
}
