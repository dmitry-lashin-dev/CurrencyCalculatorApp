package com.example.currencycalculatorapp.data.source

import com.example.currencycalculatorapp.data.source.api.CurrencyCalculatorApi
import retrofit2.Response

class CurrenciesRatesDataSource {

    private val api: CurrencyCalculatorApi by lazy {
        CurrencyCalculatorApi.create()
    }

    suspend fun getAllRates(date: String): Response<Any> {
        return api.getAllRatesByDate(date, CurrencyCalculatorApi.RESPONSE_TYPE)
    }
}