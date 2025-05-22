package com.example.divinationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar // For potential future use with DatePicker

class HoroscopeActivity : AppCompatActivity() {

    // UI Member Variables
    private lateinit var horoscopeTitleTextView: TextView
    private lateinit var datePromptTextView: TextView
    private lateinit var birthDatePicker: DatePicker
    private lateinit var getPredictionsButton: Button
    private lateinit var zodiacSignTextView: TextView
    private lateinit var predictionOptionsLinearLayout: LinearLayout
    private lateinit var predict3hButton: Button
    private lateinit var predict12hButton: Button
    private lateinit var predict24hButton: Button
    private lateinit var predictionProgressBar: ProgressBar
    private lateinit var predictionScrollView: ScrollView
    private lateinit var predictionTextView: TextView

    // Gemini API
    private var generativeModel: GenerativeModel? = null
    private var currentZodiacSign: String? = null
    private val TAG = "HoroscopeActivity" // For logging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horoscope)

        // Initialize UI Member Variables
        horoscopeTitleTextView = findViewById(R.id.horoscope_title_textview)
        datePromptTextView = findViewById(R.id.date_prompt_textview)
        birthDatePicker = findViewById(R.id.birth_date_picker)
        getPredictionsButton = findViewById(R.id.get_predictions_button)
        zodiacSignTextView = findViewById(R.id.zodiac_sign_textview)
        predictionOptionsLinearLayout = findViewById(R.id.prediction_options_linearlayout)
        predict3hButton = findViewById(R.id.predict_3h_button)
        predict12hButton = findViewById(R.id.predict_12h_button)
        predict24hButton = findViewById(R.id.predict_24h_button)
        predictionProgressBar = findViewById(R.id.prediction_progressbar)
        predictionScrollView = findViewById(R.id.prediction_scrollview)
        predictionTextView = findViewById(R.id.prediction_textview)

        // Set initial visibility for elements that appear after date selection
        zodiacSignTextView.visibility = View.GONE
        predictionOptionsLinearLayout.visibility = View.GONE
        predictionProgressBar.visibility = View.GONE
        predictionScrollView.visibility = View.GONE

        // Initialize Gemini API
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isBlank() || apiKey == "YOUR_API_KEY_HERE") {
            val errorMsg = getString(R.string.error_api_key_not_configured)
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
            predictionTextView.text = errorMsg
            predictionTextView.visibility = View.VISIBLE
            predictionScrollView.visibility = View.VISIBLE
            disablePredictionButtons()
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
                predictionTextView.text = errorMsg
                predictionTextView.visibility = View.VISIBLE
                predictionScrollView.visibility = View.VISIBLE
                disablePredictionButtons()
            }
        }

        getPredictionsButton.setOnClickListener {
            handleGetPredictionsClick()
        }

        predict3hButton.setOnClickListener {
            currentZodiacSign?.let { sign ->
                fetchHoroscopePrediction(getString(R.string.predict_3h_button_text), sign)
            } ?: Toast.makeText(this, getString(R.string.error_determine_zodiac_sign_first), Toast.LENGTH_SHORT).show()
        }
        predict12hButton.setOnClickListener {
            currentZodiacSign?.let { sign ->
                fetchHoroscopePrediction(getString(R.string.predict_12h_button_text), sign)
            } ?: Toast.makeText(this, getString(R.string.error_determine_zodiac_sign_first), Toast.LENGTH_SHORT).show()
        }
        predict24hButton.setOnClickListener {
            currentZodiacSign?.let { sign ->
                fetchHoroscopePrediction(getString(R.string.predict_24h_button_text), sign)
            } ?: Toast.makeText(this, getString(R.string.error_determine_zodiac_sign_first), Toast.LENGTH_SHORT).show()
        }
    }

    private fun disablePredictionButtons() {
        predict3hButton.isEnabled = false
        predict12hButton.isEnabled = false
        predict24hButton.isEnabled = false
    }

    private fun handleGetPredictionsClick() {
        val day = birthDatePicker.dayOfMonth
        val month = birthDatePicker.month
        currentZodiacSign = getZodiacSign(day, month)

        zodiacSignTextView.text = getString(R.string.text_your_zodiac_sign, currentZodiacSign)
        zodiacSignTextView.visibility = View.VISIBLE
        predictionOptionsLinearLayout.visibility = View.VISIBLE

        if (generativeModel == null) {
            disablePredictionButtons()
            Toast.makeText(this, getString(R.string.error_prediction_feature_disabled_api_key), Toast.LENGTH_LONG).show()
        } else {
            predict3hButton.isEnabled = true
            predict12hButton.isEnabled = true
            predict24hButton.isEnabled = true
        }
    }

    // Made internal for testing
    internal fun getZodiacSign(day: Int, month: Int): String {
        return when (month) {
            0 -> if (day <= 19) "Capricorn" else "Aquarius"
            1 -> if (day <= 18) "Aquarius" else "Pisces"
            2 -> if (day <= 20) "Pisces" else "Aries"
            3 -> if (day <= 19) "Aries" else "Taurus"
            4 -> if (day <= 20) "Taurus" else "Gemini"
            5 -> if (day <= 20) "Gemini" else "Cancer"
            6 -> if (day <= 22) "Cancer" else "Leo"
            7 -> if (day <= 22) "Leo" else "Virgo"
            8 -> if (day <= 22) "Virgo" else "Libra"
            9 -> if (day <= 22) "Libra" else "Scorpio"
            10 -> if (day <= 21) "Scorpio" else "Sagittarius"
            11 -> if (day <= 21) "Sagittarius" else "Capricorn"
            else -> "Unknown"
        }
    }

    private fun fetchHoroscopePrediction(duration: String, zodiacSign: String) {
        if (generativeModel == null) {
            Toast.makeText(this, getString(R.string.error_ai_model_not_available), Toast.LENGTH_LONG).show()
            return
        }

        predictionTextView.text = ""
        predictionProgressBar.visibility = View.VISIBLE
        predictionTextView.visibility = View.GONE
        predictionScrollView.visibility = View.VISIBLE

        val prompt = "Provide a detailed and insightful $duration horoscope prediction for a $zodiacSign. Focus on personal growth, potential challenges, and positive aspects. Make it engaging and about 3-4 sentences long."

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            try {
                val response = generativeModel?.generateContent(prompt)
                val responseText = response?.text?.trim()
                if (responseText.isNullOrBlank()) {
                     predictionTextView.text = getString(R.string.error_empty_prediction_received)
                } else {
                    predictionTextView.text = responseText
                }
            } catch (e: Exception) {
                Log.e(TAG, "API Error for prompt: $prompt", e)
                predictionTextView.text = getString(R.string.error_fetching_prediction, e.localizedMessage ?: "Unknown error")
            } finally {
                predictionProgressBar.visibility = View.GONE
                predictionTextView.visibility = View.VISIBLE
            }
        }
    }
}
