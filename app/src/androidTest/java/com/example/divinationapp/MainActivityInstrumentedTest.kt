package com.example.divinationapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testHomeScreenElementsDisplayed() {
        // Check App Title
        onView(withId(R.id.app_title_textview))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.app_title)))

        // Check Tarot Card
        onView(withId(R.id.tarot_cardview))
            .check(matches(isDisplayed()))
        onView(allOf(withText(R.string.tarot_reading), isDescendantOfA(withId(R.id.tarot_cardview))))
            .check(matches(isDisplayed()))

        // Check Astrology Card
        onView(withId(R.id.astrology_cardview))
            .check(matches(isDisplayed()))
        onView(allOf(withText(R.string.astrology), isDescendantOfA(withId(R.id.astrology_cardview))))
            .check(matches(isDisplayed()))

        // Check Numerology Card
        onView(withId(R.id.numerology_cardview))
            .check(matches(isDisplayed()))
        onView(allOf(withText(R.string.numerology), isDescendantOfA(withId(R.id.numerology_cardview))))
            .check(matches(isDisplayed()))

        // Check Daily Horoscope Card
        onView(withId(R.id.daily_horoscope_cardview))
            .check(matches(isDisplayed()))
        onView(allOf(withText(R.string.daily_horoscope), isDescendantOfA(withId(R.id.daily_horoscope_cardview))))
            .check(matches(isDisplayed()))
            
        // Check Daily Wisdom TextView
        onView(withId(R.id.daily_wisdom_textview))
            .check(matches(isDisplayed()))
        // We don't check the specific text of daily_wisdom_textview as it's random,
        // but we can check if it's not empty or matches one of the possible quotes if needed.
        // For this basic test, just checking if it's displayed is sufficient.
    }
}
