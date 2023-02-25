package com.example.currencycalculatorapp.data.source

import com.example.currencycalculatorapp.data.source.api.CurrencyCalculatorApi
import com.example.currencycalculatorapp.data.source.api.CurrencyCalculatorApi.Companion.RESPONSE_TYPE
import com.example.currencycalculatorapp.domain.models.dto.CurrencyRateDto
import retrofit2.Response

class CurrencyRateDataSource {

    private val api: CurrencyCalculatorApi by lazy {
        CurrencyCalculatorApi.create()
    }

    suspend fun getCurrencyRate(ticker: String, date: String): Response<CurrencyRateDto> {
        return api.getCurrencyRateByDateAndTicker(ticker, date, RESPONSE_TYPE)
    }
}