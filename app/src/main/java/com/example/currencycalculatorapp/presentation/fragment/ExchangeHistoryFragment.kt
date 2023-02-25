package com.example.currencycalculatorapp.presentation.fragment

import com.example.currencycalculator.databinding.FragmentExchangeHistoryBinding
import com.example.currencycalculatorapp.presentation.base.BaseFragment

class ExchangeHistoryFragment : BaseFragment<FragmentExchangeHistoryBinding>() {

    override fun inflateViewBinding(): FragmentExchangeHistoryBinding {
        return FragmentExchangeHistoryBinding.inflate(layoutInflater)
    }
}