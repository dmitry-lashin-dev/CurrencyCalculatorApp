package com.example.currencycalculatorapp.domain.models.presentation

data class ExchangeTransitionModel(
    val ratesModel: CurrencyDataModel,
    val originRate: Double,
    var targetRate: Double = 1.0,
    var targetTicker: String = ""
)
