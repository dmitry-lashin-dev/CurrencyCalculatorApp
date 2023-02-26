package com.example.currencycalculatorapp.presentation.fragment

import androidx.lifecycle.asLiveData
import com.example.currencycalculatorapp.data.models.dto.ExchangeHistoryDto
import com.example.currencycalculatorapp.domain.GetExchangeHistoryUseCase
import com.example.currencycalculatorapp.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class ExchangeHistoryViewModel(
    private val getExchangeHistoryUseCase: GetExchangeHistoryUseCase = GetExchangeHistoryUseCase()
) : BaseViewModel() {

    private val historyFlow = MutableStateFlow<List<ExchangeHistoryDto>>(emptyList())

    val historyLd = historyFlow
        .map {
            it to it.isEmpty()
        }
        .asLiveData()

    fun loadHistory() {
        getExchangeHistoryUseCase.execute {
            onComplete {
                historyFlow.tryEmit(it)
            }
        }
    }

}