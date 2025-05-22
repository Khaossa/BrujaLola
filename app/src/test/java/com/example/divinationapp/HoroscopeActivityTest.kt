package com.example.divinationapp

import org.junit.Assert.*
import org.junit.Test

class HoroscopeActivityTest {

    private val activity = HoroscopeActivity() // Instance to test getZodiacSign

    @Test
    fun testGetZodiacSign_Aries() {
        assertEquals("Aries", activity.getZodiacSign(21, 2)) // March 21
        assertEquals("Aries", activity.getZodiacSign(1, 3))  // April 1
        assertEquals("Aries", activity.getZodiacSign(19, 3)) // April 19
    }

    @Test
    fun testGetZodiacSign_Taurus() {
        assertEquals("Taurus", activity.getZodiacSign(20, 3)) // April 20
        assertEquals("Taurus", activity.getZodiacSign(1, 4))  // May 1
        assertEquals("Taurus", activity.getZodiacSign(20, 4)) // May 20
    }

    @Test
    fun testGetZodiacSign_Gemini() {
        assertEquals("Gemini", activity.getZodiacSign(21, 4)) // May 21
        assertEquals("Gemini", activity.getZodiacSign(1, 5))  // June 1
        assertEquals("Gemini", activity.getZodiacSign(20, 5)) // June 20
    }

    @Test
    fun testGetZodiacSign_Cancer() {
        assertEquals("Cancer", activity.getZodiacSign(21, 5)) // June 21
        assertEquals("Cancer", activity.getZodiacSign(1, 6))  // July 1
        assertEquals("Cancer", activity.getZodiacSign(22, 6)) // July 22
    }

    @Test
    fun testGetZodiacSign_Leo() {
        assertEquals("Leo", activity.getZodiacSign(23, 6)) // July 23
        assertEquals("Leo", activity.getZodiacSign(1, 7))  // August 1
        assertEquals("Leo", activity.getZodiacSign(22, 7)) // August 22
    }

    @Test
    fun testGetZodiacSign_Virgo() {
        assertEquals("Virgo", activity.getZodiacSign(23, 7)) // August 23
        assertEquals("Virgo", activity.getZodiacSign(1, 8))  // September 1
        assertEquals("Virgo", activity.getZodiacSign(22, 8)) // September 22
    }

    @Test
    fun testGetZodiacSign_Libra() {
        assertEquals("Libra", activity.getZodiacSign(23, 8)) // September 23
        assertEquals("Libra", activity.getZodiacSign(1, 9))  // October 1
        assertEquals("Libra", activity.getZodiacSign(22, 9)) // October 22
    }

    @Test
    fun testGetZodiacSign_Scorpio() {
        assertEquals("Scorpio", activity.getZodiacSign(23, 9)) // October 23
        assertEquals("Scorpio", activity.getZodiacSign(1, 10)) // November 1
        assertEquals("Scorpio", activity.getZodiacSign(21, 10))// November 21
    }

    @Test
    fun testGetZodiacSign_Sagittarius() {
        assertEquals("Sagittarius", activity.getZodiacSign(22, 10)) // November 22
        assertEquals("Sagittarius", activity.getZodiacSign(1, 11))  // December 1
        assertEquals("Sagittarius", activity.getZodiacSign(21, 11)) // December 21
    }

    @Test
    fun testGetZodiacSign_Capricorn() {
        assertEquals("Capricorn", activity.getZodiacSign(22, 11)) // December 22
        assertEquals("Capricorn", activity.getZodiacSign(1, 0))   // January 1
        assertEquals("Capricorn", activity.getZodiacSign(19, 0))  // January 19
    }

    @Test
    fun testGetZodiacSign_Aquarius() {
        assertEquals("Aquarius", activity.getZodiacSign(20, 0)) // January 20
        assertEquals("Aquarius", activity.getZodiacSign(1, 1))  // February 1
        assertEquals("Aquarius", activity.getZodiacSign(18, 1)) // February 18
    }

    @Test
    fun testGetZodiacSign_Pisces() {
        assertEquals("Pisces", activity.getZodiacSign(19, 1)) // February 19
        assertEquals("Pisces", activity.getZodiacSign(1, 2))  // March 1
        assertEquals("Pisces", activity.getZodiacSign(20, 2)) // March 20
    }

    @Test
    fun testGetZodiacSign_Unknown() {
        // Assuming month is 0-11, this case should not be reachable with valid DatePicker values.
        // However, if the method were public and could receive any int:
        assertEquals("Unknown", activity.getZodiacSign(1, 12)) // Invalid month
    }
}
