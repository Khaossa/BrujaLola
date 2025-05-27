package com.example.divinationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols

class AstralChartDisplayActivity : AppCompatActivity() {

    private lateinit var birthDataDisplayTextView: TextView
    private lateinit var sunSignDisplayTextView: TextView
    private lateinit var simpleInterpretationButton: Button
    private lateinit var detailedInterpretationButton: Button
    private lateinit var interpretationProgressBar: ProgressBar
    private lateinit var interpretationResponseTextView: TextView

    private var generativeModel: GenerativeModel? = null
    private var currentSunSign: String? = null // Changed from sunSign to currentSunSign for clarity
    private val TAG = "AstralChartDisplay"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_astral_display)

        // Initialize UI elements
        birthDataDisplayTextView = findViewById(R.id.birth_data_display_textview)
        sunSignDisplayTextView = findViewById(R.id.sun_sign_display_textview)
        simpleInterpretationButton = findViewById(R.id.simple_interpretation_button)
        detailedInterpretationButton = findViewById(R.id.detailed_interpretation_button)
        interpretationProgressBar = findViewById(R.id.interpretation_progressbar)
        interpretationResponseTextView = findViewById(R.id.interpretation_response_textview)

        // Retrieve data from Intent
        val year = intent.getIntExtra("YEAR", -1)
        val month = intent.getIntExtra("MONTH", -1) // 0-indexed
        val day = intent.getIntExtra("DAY", -1)
        currentSunSign = intent.getStringExtra("SUN_SIGN") // Store in member variable

        // Validate received data (basic check)
        if (year == -1 || month == -1 || day == -1 || currentSunSign.isNullOrEmpty()) {
            birthDataDisplayTextView.text = getString(R.string.error_could_not_retrieve_birth_data)
            sunSignDisplayTextView.text = ""
            disableInterpretationButtons()
            return
        }

        // Format and display Date of Birth
        val monthName = DateFormatSymbols().months[month]
        birthDataDisplayTextView.text = getString(R.string.birth_data_display_label, day, monthName, year)

        // Display Sun Sign
        sunSignDisplayTextView.text = getString(R.string.sun_sign_display_label, currentSunSign)

        // Set initial visibility for interpretation elements
        interpretationProgressBar.visibility = View.GONE
        interpretationResponseTextView.visibility = View.GONE

        // Initialize Gemini API
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "YOUR_API_KEY_HERE") {
            val errorMsg = getString(R.string.error_api_key_not_configured)
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
            interpretationResponseTextView.text = errorMsg
            interpretationResponseTextView.visibility = View.VISIBLE
            disableInterpretationButtons()
        } else {
            try {
                generativeModel = GenerativeModel(
                    modelName = "gemini-pro",
                    apiKey = apiKey
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing GenerativeModel", e)
                val errorMsg = getString(R.string.error_initializing_ai_model, e.localizedMessage ?: "Unknown error")
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
                interpretationResponseTextView.text = errorMsg
                interpretationResponseTextView.visibility = View.VISIBLE
                disableInterpretationButtons()
            }
        }

        simpleInterpretationButton.setOnClickListener {
            fetchInterpretation(isDetailed = false)
        }

        detailedInterpretationButton.setOnClickListener {
            fetchInterpretation(isDetailed = true)
        }
    }

    private fun disableInterpretationButtons() {
        simpleInterpretationButton.isEnabled = false
        detailedInterpretationButton.isEnabled = false
    }

    private fun fetchInterpretation(isDetailed: Boolean) {
        if (currentSunSign.isNullOrEmpty()) {
            interpretationResponseTextView.text = getString(R.string.error_sun_sign_not_available)
            interpretationResponseTextView.visibility = View.VISIBLE
            Toast.makeText(this, getString(R.string.error_sun_sign_not_available), Toast.LENGTH_SHORT).show()
            return
        }

        if (generativeModel == null) {
            interpretationResponseTextView.text = getString(R.string.error_ai_model_not_ready)
            interpretationResponseTextView.visibility = View.VISIBLE
            Toast.makeText(this, getString(R.string.error_ai_model_not_ready), Toast.LENGTH_LONG).show()
            return
        }

        interpretationProgressBar.visibility = View.VISIBLE
        interpretationResponseTextView.visibility = View.GONE
        interpretationResponseTextView.text = "" // Clear previous text

        val prompt = if (isDetailed) {
            getString(R.string.prompt_detailed_sun_sign_interpretation, currentSunSign)
        } else {
            getString(R.string.prompt_simple_sun_sign_interpretation, currentSunSign)
        }

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            try {
                val response = generativeModel?.generateContent(prompt)
                val responseText = response?.text?.trim()
                if (responseText.isNullOrBlank()) {
                    interpretationResponseTextView.text = getString(R.string.error_empty_prediction_received)
                } else {
                    interpretationResponseTextView.text = responseText
                }
            } catch (e: Exception) {
                Log.e(TAG, "API Error for prompt: $prompt", e)
                interpretationResponseTextView.text = getString(R.string.error_fetching_prediction, e.localizedMessage ?: "Unknown error")
            } finally {
                interpretationProgressBar.visibility = View.GONE
                interpretationResponseTextView.visibility = View.VISIBLE
            }
        }
    }
}
