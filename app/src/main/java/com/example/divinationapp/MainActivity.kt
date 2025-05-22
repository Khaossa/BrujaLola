package com.example.divinationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var tarotCardView: MaterialCardView
    private lateinit var astrologyCardView: MaterialCardView
    private lateinit var numerologyCardView: MaterialCardView
    private lateinit var dailyHoroscopeCardView: MaterialCardView
    private lateinit var dailyWisdomTextView: TextView

    private val wisdomQuotes = listOf(
        "The best way to predict the future is to create it.",
        "Trust the vibes you get, energy doesn't lie.",
        "The journey of a thousand miles begins with a single step.",
        "What is coming is better than what is gone.",
        "Embrace the mystery of the unknown."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views
        tarotCardView = findViewById(R.id.tarot_cardview)
        astrologyCardView = findViewById(R.id.astrology_cardview)
        numerologyCardView = findViewById(R.id.numerology_cardview)
        dailyHoroscopeCardView = findViewById(R.id.daily_horoscope_cardview)
        dailyWisdomTextView = findViewById(R.id.daily_wisdom_textview)

        // Set click listeners
        tarotCardView.setOnClickListener {
            showToast(getString(R.string.tarot_reading) + " clicked!")
        }
        astrologyCardView.setOnClickListener {
            showToast(getString(R.string.astrology) + " clicked!")
        }
        numerologyCardView.setOnClickListener {
            showToast(getString(R.string.numerology) + " clicked!")
        }
        dailyHoroscopeCardView.setOnClickListener {
            showToast(getString(R.string.daily_horoscope) + " clicked!")
        }

        // Set daily wisdom
        val randomIndex = Random.nextInt(wisdomQuotes.size)
        dailyWisdomTextView.text = wisdomQuotes[randomIndex]
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
