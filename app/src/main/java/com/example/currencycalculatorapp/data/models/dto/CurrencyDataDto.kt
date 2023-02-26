package com.example.currencycalculatorapp.data.models.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyDataDto(
    @SerializedName("r030") val currencyId: Int,
    @SerializedName("txt") val currencyName: String,
    @SerializedName("rate") val rate: Double,
    @SerializedName("cc") val currencyTicker: String,
    @SerializedName("exchangedate") val exchangeDate: String
) : Parcelable
