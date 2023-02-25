package com.example.currencycalculatorapp.data.source

import com.example.currencycalculatorapp.data.source.api.CurrencyCalculatorApi
import com.example.currencycalculatorapp.domain.models.dto.CurrencyDataDto
import retrofit2.Response

class CurrenciesRatesDataSource {

    private val api: CurrencyCalculatorApi by lazy {
        CurrencyCalculatorApi.create()
    }

    suspend fun getAllRates(date: String): Response<List<CurrencyDataDto>> {
        return api.getAllRatesByDate(date, CurrencyCalculatorApi.RESPONSE_TYPE)
    }
}