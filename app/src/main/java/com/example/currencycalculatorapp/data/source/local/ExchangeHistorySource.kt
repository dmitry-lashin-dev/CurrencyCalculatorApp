package com.example.currencycalculatorapp.data.source.local

import com.example.currencycalculatorapp.data.models.dto.ExchangeHistoryDto
import com.example.currencycalculatorapp.data.source.local.prefs.PrefsHelper

class ExchangeHistorySource {

    private val prefs: PrefsHelper by lazy {
        PrefsHelper.getInstance()
    }

    fun saveExchangeHistoryItem(model: ExchangeHistoryDto) {
        prefs.saveHistoryItem(model)
    }

    fun getHistory(): List<ExchangeHistoryDto> {
        return prefs.getExchangeHistory()
    }
}