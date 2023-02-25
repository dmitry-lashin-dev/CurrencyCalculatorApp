package com.example.currencycalculatorapp.utils

import android.util.Log
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private const val TAG = "DateUtil"

    const val FULL_TIME_FORMAT = "dd.MM.yy HH:mm:ss"
    const val FULL_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss"
    const val TRANSACTION_TIME_FORMAT = "HH:mm dd MMMM yy"
    const val MONTH_TIME_FORMAT = "MMM dd"
    const val DATE_FORMAT = "dd.MM.yyyy"
    const val FULL_MONTH_DATE_FORMAT = "dd MMMM yyyy"
    const val DATE_PATTERN = "dd MMM yy"
    const val DAY_MONTH_YEAR_DATE_FORMAT = "dd MMM yyyy"
    const val HOURS_MINUTES_DATE_FORMAT = "HH:mm"
    const val AML_RECORD_DATE_FORMAT = "dd MMM yyyy, HH:mm"
    const val KYC_DATE_FORMAT = "yyyy-MM-dd"
    const val HOURS_MINUTES_SECONDS_FORMAT = "HH:mm:ss"
    const val MONTH_DAY_HOURS_MINUTES_FORMAT = "MM|dd,HH:mm"

    const val CURRENCY_REQUEST_DATE_FORMAT = "yyyymmdd"

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

    fun getCalendar(epochDay: Long): Calendar {
        val cal = Calendar.getInstance(Locale.getDefault())
        cal.time = Date(epochDay)
        return cal
    }
}