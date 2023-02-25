package com.example.currencycalculatorapp.data.source.api

import com.example.currencycalculatorapp.data.source.apiClient
import com.example.currencycalculatorapp.domain.models.dto.CurrencyRateDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyCalculatorApi {

    companion object {
        const val RESPONSE_TYPE = "json"
        private const val BASE_URL = "https://bank.gov.ua/NBUStatService/"

        fun create(): CurrencyCalculatorApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(apiClient)
                .build()
                .create(CurrencyCalculatorApi::class.java)
        }
    }

    @GET("v1/statdirectory/exchange")
    suspend fun getCurrencyRateByDateAndTicker(
        @Query("valcode") ticker: String,
        @Query("date") date: String,
        @Query("json") type: String
    ): Response<CurrencyRateDto>

    @GET("v1/statdirectory/exchange")
    suspend fun getAllRatesByDate(
        @Query("date") date: String,
        @Query("json") type: String
    ) : Response<Any>
}