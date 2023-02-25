package com.example.currencycalculatorapp.presentation.fragment

import com.example.currencycalculator.databinding.FragmentCurrencyBinding
import com.example.currencycalculatorapp.presentation.base.BaseFragment

class CurrencyCalculatorFragment : BaseFragment<FragmentCurrencyBinding>() {

    override fun inflateViewBinding(): FragmentCurrencyBinding {
        return FragmentCurrencyBinding.inflate(layoutInflater)
    }
}