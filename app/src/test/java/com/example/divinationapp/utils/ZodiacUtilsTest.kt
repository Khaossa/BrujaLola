package com.example.divinationapp.utils

import org.junit.Assert.*
import org.junit.Test

class ZodiacUtilsTest {

    @Test
    fun getSunSign_Aries() {
        assertEquals("Aries", ZodiacUtils.getSunSign(21, 2)) // March 21
        assertEquals("Aries", ZodiacUtils.getSunSign(1, 3))  // April 1
        assertEquals("Aries", ZodiacUtils.getSunSign(19, 3)) // April 19
        assertNotEquals("Aries", ZodiacUtils.getSunSign(20, 2)) // March 20 should be Pisces
        assertNotEquals("Aries", ZodiacUtils.getSunSign(20, 3)) // April 20 should be Taurus
    }

    @Test
    fun getSunSign_Taurus() {
        assertEquals("Taurus", ZodiacUtils.getSunSign(20, 3)) // April 20
        assertEquals("Taurus", ZodiacUtils.getSunSign(1, 4))  // May 1
        assertEquals("Taurus", ZodiacUtils.getSunSign(20, 4)) // May 20
        assertNotEquals("Taurus", ZodiacUtils.getSunSign(19, 3))
        assertNotEquals("Taurus", ZodiacUtils.getSunSign(21, 4))
    }

    @Test
    fun getSunSign_Gemini() {
        assertEquals("Gemini", ZodiacUtils.getSunSign(21, 4)) // May 21
        assertEquals("Gemini", ZodiacUtils.getSunSign(1, 5))  // June 1
        assertEquals("Gemini", ZodiacUtils.getSunSign(20, 5)) // June 20
        assertNotEquals("Gemini", ZodiacUtils.getSunSign(20, 4))
        assertNotEquals("Gemini", ZodiacUtils.getSunSign(21, 5))
    }

    @Test
    fun getSunSign_Cancer() {
        assertEquals("Cancer", ZodiacUtils.getSunSign(21, 5)) // June 21
        assertEquals("Cancer", ZodiacUtils.getSunSign(1, 6))  // July 1
        assertEquals("Cancer", ZodiacUtils.getSunSign(22, 6)) // July 22
        assertNotEquals("Cancer", ZodiacUtils.getSunSign(20, 5))
        assertNotEquals("Cancer", ZodiacUtils.getSunSign(23, 6))
    }

    @Test
    fun getSunSign_Leo() {
        assertEquals("Leo", ZodiacUtils.getSunSign(23, 6)) // July 23
        assertEquals("Leo", ZodiacUtils.getSunSign(1, 7))  // August 1
        assertEquals("Leo", ZodiacUtils.getSunSign(22, 7)) // August 22
        assertNotEquals("Leo", ZodiacUtils.getSunSign(22, 6))
        assertNotEquals("Leo", ZodiacUtils.getSunSign(23, 7))
    }

    @Test
    fun getSunSign_Virgo() {
        assertEquals("Virgo", ZodiacUtils.getSunSign(23, 7)) // August 23
        assertEquals("Virgo", ZodiacUtils.getSunSign(1, 8))  // September 1
        assertEquals("Virgo", ZodiacUtils.getSunSign(22, 8)) // September 22
        assertNotEquals("Virgo", ZodiacUtils.getSunSign(22, 7))
        assertNotEquals("Virgo", ZodiacUtils.getSunSign(23, 8))
    }

    @Test
    fun getSunSign_Libra() {
        assertEquals("Libra", ZodiacUtils.getSunSign(23, 8)) // September 23
        assertEquals("Libra", ZodiacUtils.getSunSign(1, 9))  // October 1
        assertEquals("Libra", ZodiacUtils.getSunSign(22, 9)) // October 22
        assertNotEquals("Libra", ZodiacUtils.getSunSign(22, 8))
        assertNotEquals("Libra", ZodiacUtils.getSunSign(23, 9))
    }

    @Test
    fun getSunSign_Scorpio() {
        assertEquals("Scorpio", ZodiacUtils.getSunSign(23, 9)) // October 23
        assertEquals("Scorpio", ZodiacUtils.getSunSign(1, 10)) // November 1
        assertEquals("Scorpio", ZodiacUtils.getSunSign(21, 10))// November 21
        assertNotEquals("Scorpio", ZodiacUtils.getSunSign(22, 9))
        assertNotEquals("Scorpio", ZodiacUtils.getSunSign(22, 10))
    }

    @Test
    fun getSunSign_Sagittarius() {
        assertEquals("Sagittarius", ZodiacUtils.getSunSign(22, 10)) // November 22
        assertEquals("Sagittarius", ZodiacUtils.getSunSign(1, 11))  // December 1
        assertEquals("Sagittarius", ZodiacUtils.getSunSign(21, 11)) // December 21
        assertNotEquals("Sagittarius", ZodiacUtils.getSunSign(21, 10))
        assertNotEquals("Sagittarius", ZodiacUtils.getSunSign(22, 11))
    }

    @Test
    fun getSunSign_Capricorn() {
        assertEquals("Capricorn", ZodiacUtils.getSunSign(22, 11)) // December 22
        assertEquals("Capricorn", ZodiacUtils.getSunSign(1, 0))   // January 1
        assertEquals("Capricorn", ZodiacUtils.getSunSign(19, 0))  // January 19
        assertNotEquals("Capricorn", ZodiacUtils.getSunSign(21, 11))
        assertNotEquals("Capricorn", ZodiacUtils.getSunSign(20, 0))
    }

    @Test
    fun getSunSign_Aquarius() {
        assertEquals("Aquarius", ZodiacUtils.getSunSign(20, 0)) // January 20
        assertEquals("Aquarius", ZodiacUtils.getSunSign(1, 1))  // February 1
        assertEquals("Aquarius", ZodiacUtils.getSunSign(18, 1)) // February 18
        assertNotEquals("Aquarius", ZodiacUtils.getSunSign(19, 0))
        assertNotEquals("Aquarius", ZodiacUtils.getSunSign(19, 1))
    }

    @Test
    fun getSunSign_Pisces() {
        assertEquals("Pisces", ZodiacUtils.getSunSign(19, 1)) // February 19
        assertEquals("Pisces", ZodiacUtils.getSunSign(1, 2))  // March 1
        assertEquals("Pisces", ZodiacUtils.getSunSign(20, 2)) // March 20
        assertNotEquals("Pisces", ZodiacUtils.getSunSign(18, 1))
        assertNotEquals("Pisces", ZodiacUtils.getSunSign(21, 2))
    }

    @Test
    fun getSunSign_Unknown() {
        assertEquals("Unknown", ZodiacUtils.getSunSign(1, 12)) // Invalid month
        assertEquals("Unknown", ZodiacUtils.getSunSign(1, -1)) // Invalid month
    }
}
