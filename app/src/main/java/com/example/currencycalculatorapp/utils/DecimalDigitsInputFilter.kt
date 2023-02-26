package com.example.currencycalculatorapp.utils

import android.text.InputFilter
import android.text.Spanned


class DecimalDigitsInputFilter(
    private val digitsAfterZero: Int
) : InputFilter {

    companion object {
        private const val DOT_SYMBOL = "."
        private const val COMA_SYMBOL = ","
    }

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val regex = "[0-9]+\\.?[0-9]{0,${digitsAfterZero}}".toRegex()
        val currentValue = dest.toString()
        val currentLength = dest.toString().length
        val newSymbol = source.toString()
        val finalValue = if (dstart == currentLength) {
            "$currentValue$newSymbol"
        } else {
            val builder = StringBuilder(currentValue)
            builder.insert(dstart,newSymbol)
            builder.toString()
        }
        return if (!finalValue.matches(regex)) {
            ""
        } else {
            if (currentValue.contains(newSymbol) && (newSymbol == COMA_SYMBOL || newSymbol == DOT_SYMBOL)) {
                ""
            } else if ((currentValue.contains(DOT_SYMBOL) || currentValue.contains(COMA_SYMBOL)) && (newSymbol == DOT_SYMBOL || newSymbol == COMA_SYMBOL)) {
                ""
            } else {
                null
            }
        }
    }
}