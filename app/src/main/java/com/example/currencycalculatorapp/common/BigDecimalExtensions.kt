package com.example.currencycalculatorapp.common

import java.math.BigDecimal

fun BigDecimal?.toPrecision(prec: Int?, roundingMode: Int = BigDecimal.ROUND_DOWN): String {
    val zero = BigDecimal.ZERO
    if (this == null || this.isZero()) {
        return zero.toPlainString()
    }

    return this.setScale(prec ?: 4, roundingMode)
        .stripZeros()
        .toPlainString()
}

fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0

fun BigDecimal.stripZeros(): BigDecimal {
    val zero = BigDecimal.ZERO

    return if (this.compareTo(zero) == 0) {
        zero
    } else {
        stripTrailingZeros()
    }
}