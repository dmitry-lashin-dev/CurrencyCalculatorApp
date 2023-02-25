package com.example.currencycalculatorapp.domain.models.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyDataUi(
    val name: String,
    val ticker: String
): Parcelable
