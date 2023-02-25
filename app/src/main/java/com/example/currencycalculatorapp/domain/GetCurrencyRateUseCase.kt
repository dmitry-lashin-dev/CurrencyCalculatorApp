package com.example.currencycalculatorapp.domain

import com.example.currencycalculatorapp.data.source.CurrencyRateDataSource
import com.example.currencycalculatorapp.domain.base.BaseUseCase
import com.example.currencycalculatorapp.domain.models.dto.CurrencyRateDto

class GetCurrencyRateUseCase(
    private val source: CurrencyRateDataSource = CurrencyRateDataSource()
) : BaseUseCase<CurrencyRateDto?>() {

    var ticker: String? = null
    var date: String? = null

    override suspend fun executeOnBackground(): CurrencyRateDto? {
        checkNotNull(ticker)
        checkNotNull(date)
        val result = source.getCurrencyRate(ticker!!, date!!)
        if (!result.isSuccessful) {
            throw IllegalStateException(result.errorBody()?.string() ?: "Something went wrong")
        } else {
            return result.body()
        }
    }
}