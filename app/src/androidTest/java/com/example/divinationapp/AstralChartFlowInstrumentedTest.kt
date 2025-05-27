package com.example.divinationapp

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.divinationapp.utils.ZodiacUtils
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.DateFormatSymbols

@RunWith(AndroidJUnit4::class)
class AstralChartFlowInstrumentedTest {

    // Rule for AstralChartInputActivity - launched normally
    @get:Rule
    var inputActivityRule: ActivityScenarioRule<AstralChartInputActivity> =
        ActivityScenarioRule(AstralChartInputActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testAstralChartInputActivity_initialUI_andIntentFired() {
        // Verify initial UI of AstralChartInputActivity
        onView(withId(R.id.astral_input_title_textview))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.astral_input_title)))
        onView(withId(R.id.birth_date_picker_astral)).check(matches(isDisplayed()))
        onView(withId(R.id.calculate_placements_button))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.astral_get_sun_sign_button_text)))

        // Perform date selection (e.g., March 21, 2000 for Aries)
        // Month in PickerActions.setDate is 1-indexed
        val testYear = 2000
        val testMonthOfYear = 3 // March (1-indexed for PickerActions)
        val testDayOfMonth = 21
        val expectedSunSign = ZodiacUtils.getSunSign(testDayOfMonth, testMonthOfYear - 1) // month 0-indexed for ZodiacUtils

        onView(withId(R.id.birth_date_picker_astral))
            .perform(PickerActions.setDate(testYear, testMonthOfYear, testDayOfMonth))

        // Click the button
        onView(withId(R.id.calculate_placements_button)).perform(click())

        // Verify that an Intent was sent to AstralChartDisplayActivity
        Intents.intended(
            allOf(
                hasComponent(AstralChartDisplayActivity::class.java.name),
                hasExtra("YEAR", testYear),
                hasExtra("MONTH", testMonthOfYear - 1), // month is 0-indexed in intent
                hasExtra("DAY", testDayOfMonth),
                hasExtra("SUN_SIGN", expectedSunSign)
            )
        )
    }

    @Test
    fun testAstralChartDisplayActivity_displaysDataCorrectly_andInitialUI() {
        // Create an Intent with mock data
        val testYear = 1995
        val testMonth = 7 // August (0-indexed for ZodiacUtils and Intent)
        val testDay = 23
        val testSunSign = ZodiacUtils.getSunSign(testDay, testMonth) // Leo

        val intent = Intent(ApplicationProvider.getApplicationContext(), AstralChartDisplayActivity::class.java).apply {
            putExtra("YEAR", testYear)
            putExtra("MONTH", testMonth)
            putExtra("DAY", testDay)
            putExtra("SUN_SIGN", testSunSign)
        }

        // Launch AstralChartDisplayActivity with the Intent
        val displayActivityRule = ActivityScenarioRule<AstralChartDisplayActivity>(intent)
        displayActivityRule.scenario.onActivity { /* Ensure activity is launched */ }


        // Verify Date of Birth is displayed correctly
        val monthName = DateFormatSymbols().months[testMonth]
        val expectedBirthDateText = ApplicationProvider.getApplicationContext<android.content.Context>().getString(
            R.string.birth_data_display_label, testDay, monthName, testYear
        )
        onView(withId(R.id.birth_data_display_textview))
            .check(matches(isDisplayed()))
            .check(matches(withText(expectedBirthDateText)))

        // Verify Sun Sign is displayed correctly
        val expectedSunSignText = ApplicationProvider.getApplicationContext<android.content.Context>().getString(
            R.string.sun_sign_display_label, testSunSign
        )
        onView(withId(R.id.sun_sign_display_textview))
            .check(matches(isDisplayed()))
            .check(matches(withText(expectedSunSignText)))

        // Verify interpretation buttons are visible
        onView(withId(R.id.simple_interpretation_button))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.simple_interpretation_button_text)))
        onView(withId(R.id.detailed_interpretation_button))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.detailed_interpretation_button_text)))

        // Verify ProgressBar and response TextView are initially hidden
        onView(withId(R.id.interpretation_progressbar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.interpretation_response_textview)).check(matches(not(isDisplayed())))
    }


    @Test
    fun testAstralChartDisplayActivity_clickInterpretationButton_showsProgressBarIfApiKeyValid() {
        // This test depends on a valid API key being present in BuildConfig for full path.
        // If API key is invalid, buttons should be disabled.

        val testYear = 1990
        val testMonth = 0 // January
        val testDay = 1
        val testSunSign = ZodiacUtils.getSunSign(testDay, testMonth) // Capricorn

        val intent = Intent(ApplicationProvider.getApplicationContext(), AstralChartDisplayActivity::class.java).apply {
            putExtra("YEAR", testYear)
            putExtra("MONTH", testMonth)
            putExtra("DAY", testDay)
            putExtra("SUN_SIGN", testSunSign)
        }
        val displayActivityRule = ActivityScenarioRule<AstralChartDisplayActivity>(intent)
        displayActivityRule.scenario.onActivity { /* Ensure activity is launched */ }


        // Check if API key is likely valid (simple check, real validation happens in Activity)
        val isApiKeyPotentiallyValid = BuildConfig.GEMINI_API_KEY.isNotBlank() &&
                BuildConfig.GEMINI_API_KEY != "YOUR_API_KEY_HERE"

        if (isApiKeyPotentiallyValid) {
            // Click the "Simple Interpretation" button
            onView(withId(R.id.simple_interpretation_button)).perform(click())
            // Verify ProgressBar becomes visible (actual API call is async)
            onView(withId(R.id.interpretation_progressbar)).check(matches(isDisplayed()))
        } else {
            // If API key is not valid, buttons should be disabled by onCreate logic
            onView(withId(R.id.simple_interpretation_button)).check(matches(not(isEnabled())))
            onView(withId(R.id.detailed_interpretation_button)).check(matches(not(isEnabled())))
        }
    }
}
