package com.example.currencycalculatorapp

import android.app.Application
import com.example.currencycalculatorapp.data.source.local.prefs.PrefsHelper

class CurrencyCalculatorApp: Application() {

    override fun onCreate() {
        super.onCreate()
        PrefsHelper.init(applicationContext)
    }
}