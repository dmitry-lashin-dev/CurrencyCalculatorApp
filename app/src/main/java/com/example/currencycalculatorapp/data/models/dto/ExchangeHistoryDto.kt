package com.example.currencycalculatorapp.data.models.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExchangeHistoryDto(
    val originAmount: String,
    val targetAmount: String
): Parcelable
