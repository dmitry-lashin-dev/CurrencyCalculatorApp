package com.example.currencycalculatorapp.common

import java.math.BigDecimal

fun String.toPrecision(prec: Int?, roundingMode: Int = BigDecimal.ROUND_DOWN): String {
    return if (toBigDecimalOrNull() != null) {
        this.toBigDecimal().toPrecision(prec, roundingMode)
    } else {
        "0.0"
    }
}