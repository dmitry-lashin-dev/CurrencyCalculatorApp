package com.example.currencycalculatorapp.utils

import android.util.Log
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private const val TAG = "DateUtil"

    const val CURRENCY_REQUEST_DATE_FORMAT = "yyyyMMdd"

    /**
     * Formats a timestamp to string date.
     *
     * @param time timestamp in milliseconds
     * @param toPattern the pattern describing the date and time format
     * @param locale An ISO 639 alpha-2 or alpha-3 language code, or a language subtag
     * up to 8 characters in length.  See the <code>Locale</code> class description about
     * valid language values.
     * @return the formatted time string or 'null' if an error happened.
     */
    fun format(
        time: Long,
        toPattern: String,
        locale: String
    ): String? {
        return try {
            val simpleDateFormat = SimpleDateFormat(toPattern, Locale(locale))
            val date = Date(time)
            simpleDateFormat.format(date)
        } catch (e: Exception) {
            Log.e(TAG, "Time formatting error.", e)
            null
        }
    }
}