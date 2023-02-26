package com.example.currencycalculatorapp.domain

import com.example.currencycalculatorapp.data.models.dto.ExchangeHistoryDto
import com.example.currencycalculatorapp.data.source.local.ExchangeHistorySource
import com.example.currencycalculatorapp.domain.base.BaseUseCase

class SaveExchangeHistoryUseCase(
    private val source: ExchangeHistorySource = ExchangeHistorySource()
) : BaseUseCase<Unit>() {

    var originAmountWithTicker: String? = null
    var targetAmountWithTicker: String? = null

    override suspend fun executeOnBackground() {
        checkNotNull(originAmountWithTicker)
        checkNotNull(targetAmountWithTicker)
        source.saveExchangeHistoryItem(
            ExchangeHistoryDto(
                originAmountWithTicker!!,
                targetAmountWithTicker!!
            )
        )
    }
}