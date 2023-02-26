package com.example.currencycalculatorapp.domain

import com.example.currencycalculatorapp.data.models.dto.ExchangeHistoryDto
import com.example.currencycalculatorapp.data.source.local.ExchangeHistorySource
import com.example.currencycalculatorapp.domain.base.BaseUseCase

class GetExchangeHistoryUseCase(
    private val source: ExchangeHistorySource = ExchangeHistorySource()
) : BaseUseCase<List<ExchangeHistoryDto>>() {

    override suspend fun executeOnBackground(): List<ExchangeHistoryDto> {
        return source.getHistory()
    }
}