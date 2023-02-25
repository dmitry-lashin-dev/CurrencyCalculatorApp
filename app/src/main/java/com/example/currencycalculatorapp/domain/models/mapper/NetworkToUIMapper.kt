package com.example.currencycalculatorapp.domain.models.mapper

import com.example.currencycalculatorapp.domain.models.dto.CurrencyDataDto
import com.example.currencycalculatorapp.domain.models.presentation.CurrencyDataModel
import com.example.currencycalculatorapp.domain.models.presentation.CurrencyDataUi

object NetworkToUIMapper {

    fun map(dtoList: List<CurrencyDataDto>): CurrencyDataModel {
        return CurrencyDataModel(
            currenciesNamesList = dtoList.map { map(it) },
            currenciesRatesData = dtoList.associate { it.currencyTicker to it.rate } as HashMap
        )
    }

    private fun map(dto: CurrencyDataDto): CurrencyDataUi {
        return CurrencyDataUi(
            name = dto.currencyName,
            ticker = dto.currencyTicker
        )
    }

}