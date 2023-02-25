package com.example.currencycalculatorapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.currencycalculator.databinding.FragmentCurrencyBinding
import com.example.currencycalculatorapp.presentation.base.BaseFragment

class CurrencyCalculatorFragment : BaseFragment<FragmentCurrencyBinding>() {

    private val viewModel: CurrencyCalculatorViewMode by viewModels()

    override fun inflateViewBinding(): FragmentCurrencyBinding {
        return FragmentCurrencyBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllRates()
    }
}