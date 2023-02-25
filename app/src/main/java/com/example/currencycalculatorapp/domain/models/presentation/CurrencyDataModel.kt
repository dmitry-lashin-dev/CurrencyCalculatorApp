package com.example.currencycalculatorapp.domain.models.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyDataModel(
    val currenciesNamesList: List<CurrencyDataUi>,
    val currenciesRatesData: HashMap<String, Double>
) : Parcelable
