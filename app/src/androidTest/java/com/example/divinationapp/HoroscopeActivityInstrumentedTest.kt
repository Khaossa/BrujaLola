package com.example.divinationapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HoroscopeActivityInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<HoroscopeActivity> = ActivityScenarioRule(HoroscopeActivity::class.java)

    @Test
    fun testInitialUIState() {
        // Check title
        onView(withId(R.id.horoscope_title_textview))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.horoscope_prediction_title)))

        // Check DatePicker
        onView(withId(R.id.birth_date_picker)).check(matches(isDisplayed()))

        // Check "Get Predictions" button
        onView(withId(R.id.get_predictions_button))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))
            .check(matches(withText(R.string.get_predictions_button_text)))

        // Check initially hidden elements
        onView(withId(R.id.zodiac_sign_textview)).check(matches(not(isDisplayed())))
        onView(withId(R.id.prediction_options_linearlayout)).check(matches(not(isDisplayed())))
        onView(withId(R.id.prediction_progressbar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.prediction_scrollview)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testZodiacCalculationAndUpdateUI() {
        // Select date (e.g., March 21, 2000 for Aries)
        // Note: Month in PickerActions.setDate is 1-indexed (1=January, 12=December)
        onView(withId(R.id.birth_date_picker)).perform(PickerActions.setDate(2000, 3, 21))

        // Click the "Get Predictions" button
        onView(withId(R.id.get_predictions_button)).perform(click())

        // Verify Zodiac sign TextView becomes visible and displays correct text
        onView(withId(R.id.zodiac_sign_textview))
            .check(matches(isDisplayed()))
            .check(matches(withText(activityRule.scenario.getScenarioContext().getString(R.string.text_your_zodiac_sign, "Aries"))))

        // Verify prediction options LinearLayout becomes visible
        onView(withId(R.id.prediction_options_linearlayout)).check(matches(isDisplayed()))

        // Verify prediction buttons are displayed
        onView(withId(R.id.predict_3h_button))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.predict_3h_button_text)))
        onView(withId(R.id.predict_12h_button))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.predict_12h_button_text)))
        onView(withId(R.id.predict_24h_button))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.predict_24h_button_text)))

        // This part depends on whether a valid API key is present in the test environment's BuildConfig
        // If a valid key is expected to be missing (e.g. in CI), buttons might be disabled.
        // For now, we assume it might be valid or the logic to disable them if generativeModel is null handles it.
        // A more specific test for API key missing state would be ideal if it can be reliably simulated.
    }

    @Test
    fun testPredictionButtonClick_ShowsProgressBar() {
        // Pre-condition: API Key must be valid, or this test might not behave as expected.
        // For this test, we are primarily concerned with the UI change (ProgressBar visibility)
        // and not the actual API call success.

        // Select a date and get Zodiac sign to enable prediction buttons
        onView(withId(R.id.birth_date_picker)).perform(PickerActions.setDate(2000, 1, 1)) // Jan 1
        onView(withId(R.id.get_predictions_button)).perform(click())

        // Wait for zodiac sign and options to be visible
        onView(withId(R.id.prediction_options_linearlayout)).check(matches(isDisplayed()))


        // Click the "3-hour" prediction button
        // Adding a check to ensure the button is enabled before clicking,
        // this depends on the API key state.
        // If API key is invalid by default in test, this test needs adjustment or a way to mock API key.
        // For now, proceed assuming it could be enabled.
        val isApiKeyValid = BuildConfig.GEMINI_API_KEY.isNotBlank() && BuildConfig.GEMINI_API_KEY != "YOUR_API_KEY_HERE"
        if (isApiKeyValid) {
             onView(withId(R.id.predict_3h_button)).check(matches(isEnabled())).perform(click())
            // Verify ProgressBar becomes visible
            onView(withId(R.id.prediction_progressbar)).check(matches(isDisplayed()))
        } else {
            // If API key is not valid, the button should be disabled.
             onView(withId(R.id.predict_3h_button)).check(matches(not(isEnabled())))
            // And we can't test the progress bar visibility in this path.
            // A Toast message "AI Model not available..." should appear.
            // Checking for Toasts is more complex and often skipped in basic Espresso tests or done with IdlingResources.
        }
    }

    // Helper to get string from scenario context (useful if not directly in activityRule)
    private fun <T : android.app.Activity> ActivityScenarioRule<T>.getScenarioContext(): android.content.Context {
        var context: android.content.Context? = null
        this.scenario.onActivity { activity ->
            context = activity.applicationContext
        }
        return context!!
    }
}
