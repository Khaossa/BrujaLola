package com.example.divinationapp.utils

object ZodiacUtils {
    /**
     * Calculates the Western Zodiac Sun Sign based on the given day and month.
     *
     * @param day The day of the month (1-31).
     * @param month The month of the year (0-11, where 0 is January, 11 is December).
     * @return The Zodiac sign name as a String (e.g., "Aries"), or "Unknown" if the date is invalid.
     */
    fun getSunSign(day: Int, month: Int): String {
        // month is 0-indexed (January is 0, December is 11)
        return when (month) {
            0 -> if (day <= 19) "Capricorn" else "Aquarius" // January (Capricorn ends Jan 19, Aquarius starts Jan 20)
            1 -> if (day <= 18) "Aquarius" else "Pisces"   // February (Aquarius ends Feb 18, Pisces starts Feb 19)
            2 -> if (day <= 20) "Pisces" else "Aries"      // March (Pisces ends Mar 20, Aries starts Mar 21)
            3 -> if (day <= 19) "Aries" else "Taurus"      // April (Aries ends Apr 19, Taurus starts Apr 20)
            4 -> if (day <= 20) "Taurus" else "Gemini"     // May (Taurus ends May 20, Gemini starts May 21)
            5 -> if (day <= 20) "Gemini" else "Cancer"     // June (Gemini ends Jun 20, Cancer starts Jun 21)
            6 -> if (day <= 22) "Cancer" else "Leo"        // July (Cancer ends Jul 22, Leo starts Jul 23)
            7 -> if (day <= 22) "Leo" else "Virgo"         // August (Leo ends Aug 22, Virgo starts Aug 23)
            8 -> if (day <= 22) "Virgo" else "Libra"       // September (Virgo ends Sep 22, Libra starts Sep 23)
            9 -> if (day <= 22) "Libra" else "Scorpio"     // October (Libra ends Oct 22, Scorpio starts Oct 23)
            10 -> if (day <= 21) "Scorpio" else "Sagittarius" // November (Scorpio ends Nov 21, Sagittarius starts Nov 22)
            11 -> if (day <= 21) "Sagittarius" else "Capricorn" // December (Sagittarius ends Dec 21, Capricorn starts Dec 22)
            else -> "Unknown" // Should not happen with valid DatePicker input but good for robustness
        }
    }
}
