package com.example.currencycalculatorapp.domain

import com.example.currencycalculatorapp.data.source.remote.CurrenciesRatesDataSource
import com.example.currencycalculatorapp.domain.base.BaseUseCase
import com.example.currencycalculatorapp.domain.models.mapper.NetworkToUIMapper
import com.example.currencycalculatorapp.domain.models.presentation.CurrencyDataModel
import com.example.currencycalculatorapp.utils.DateUtil
import com.example.currencycalculatorapp.utils.DateUtil.CURRENCY_REQUEST_DATE_FORMAT
import java.util.*

class GetAllRatesUseCase(
    private val source: CurrenciesRatesDataSource = CurrenciesRatesDataSource()
) : BaseUseCase<CurrencyDataModel>() {

    private val HARD_CODE_DATE = "20230225"
    var date: String? = null

    override suspend fun executeOnBackground(): CurrencyDataModel {
        val finalDate: String = if (date.isNullOrEmpty()) {
            val currentDate = System.currentTimeMillis()
            val date = DateUtil.format(
                currentDate,
                CURRENCY_REQUEST_DATE_FORMAT,
                Locale.getDefault().country
            ) ?: HARD_CODE_DATE
            date
        } else {
            date!!
        }
        val result = source.getAllRates(finalDate)
        if (!result.isSuccessful) {
            throw IllegalStateException(result.errorBody()?.string() ?: "Something went wrong")
        } else {
            val data = result.body() ?: emptyList()
            return NetworkToUIMapper.map(data)
        }
    }
}