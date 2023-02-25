package com.example.currencycalculatorapp.presentation.fragment

import android.util.Log
import com.example.currencycalculatorapp.domain.GetAllRatesUseCase
import com.example.currencycalculatorapp.domain.GetCurrencyRateUseCase
import com.example.currencycalculatorapp.domain.models.presentation.CurrencyDataModel
import com.example.currencycalculatorapp.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class CurrencyCalculatorViewMode(
    private val getCurrencyRateUseCase: GetCurrencyRateUseCase = GetCurrencyRateUseCase(),
    private val getAllRatesUseCase: GetAllRatesUseCase = GetAllRatesUseCase()
) : BaseViewModel() {

    private val ratesModelFlow = MutableStateFlow<CurrencyDataModel?>(null)

    fun getAllRates(date: String? = null) {
        showProgress()
        getAllRatesUseCase.apply {
            this.date = date
            execute {
                onComplete {
                    hideProgress()
                    ratesModelFlow.tryEmit(it)
                }
                onError {
                    hideProgress()
                    Log.i("TEST_TAG", "error")
                }
            }
        }
    }
}