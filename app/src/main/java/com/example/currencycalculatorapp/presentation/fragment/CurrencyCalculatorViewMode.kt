package com.example.currencycalculatorapp.presentation.fragment

import android.util.Log
import com.example.currencycalculatorapp.domain.GetAllRatesUseCase
import com.example.currencycalculatorapp.domain.GetCurrencyRateUseCase
import com.example.currencycalculatorapp.presentation.base.BaseViewModel

class CurrencyCalculatorViewMode(
    private val getCurrencyRateUseCase: GetCurrencyRateUseCase = GetCurrencyRateUseCase(),
    private val getAllRatesUseCase: GetAllRatesUseCase = GetAllRatesUseCase()
) : BaseViewModel() {

    fun getAllRates() {
        getAllRatesUseCase.execute {
            onComplete {
                Log.i("TEST_TAG", "success")
            }
            onError {
                Log.i("TEST_TAG", "error")
            }
        }
    }
}