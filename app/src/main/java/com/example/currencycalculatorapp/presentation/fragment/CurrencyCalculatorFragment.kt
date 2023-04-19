package com.example.currencycalculatorapp.presentation.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.currencycalculator.R
import com.example.currencycalculator.databinding.FragmentCurrencyBinding
import com.example.currencycalculatorapp.common.clickWithDebounce
import com.example.currencycalculatorapp.common.observeData
import com.example.currencycalculatorapp.domain.models.presentation.CalendarDataUi
import com.example.currencycalculatorapp.presentation.base.BaseFragment
import com.example.currencycalculatorapp.presentation.dialog.SelectCurrencyDialog
import com.example.currencycalculatorapp.utils.DecimalDigitsInputFilter

class CurrencyCalculatorFragment : BaseFragment<FragmentCurrencyBinding>() {

    companion object {
        private const val DIALOG_TAG = "tag1"
        private const val MONEY_PRECISION = 2
    }

    private val viewModel: CurrencyCalculatorViewMode by viewModels()
    private val dateListener: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.processSelectedDate(year, month, day)
        }

    override fun inflateViewBinding(): FragmentCurrencyBinding {
        return FragmentCurrencyBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeViewModelFields()
    }

    private fun setupListeners() {
        with(binding) {
            dateContainer.clickWithDebounce { viewModel.processClickOnDateContainer() }
            originalCurrencyContainer.clickWithDebounce {
                viewModel.processClickOnCurrencyContainer(
                    true
                )
            }
            targetCurrencyContainer.clickWithDebounce {
                viewModel.processClickOnCurrencyContainer(
                    false
                )
            }
            currencyAmountTiet.filters = arrayOf<InputFilter>(
                DecimalDigitsInputFilter(MONEY_PRECISION)
            )
            currencyAmountTiet.doAfterTextChanged {
                viewModel.processChangeAmount(it.toString())
            }
            exchangeTv.clickWithDebounce { viewModel.processClickExchange() }
            topToolbar.setOnMenuItemClickListener {
                return@setOnMenuItemClickListener when (it.itemId) {
                    R.id.action_history -> {
                        viewModel.processClickHistory()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun observeViewModelFields() {
        observeData(viewModel.calendarDataEvent) {
            showDatePicker(it)
        }
        observeData(viewModel.selectedDateLd) {
            binding.dateValueTv.text = it
        }
        observeData(viewModel.showSelectCurrencyDialogEvent) { data ->
            val dialog = SelectCurrencyDialog.getInstance(data.second)
            dialog.selectCurrencyListener = {
                viewModel.processSelectedCurrency(data.first, it)
            }
            dialog.show(childFragmentManager, DIALOG_TAG)
        }
        observeData(viewModel.selectedOriginalLd) {
            binding.originalCurrencyValueTv.text = it
        }
        observeData(viewModel.selectedTargetLd) {
            binding.targetCurrencyValueTv.text = it
        }
        observeData(viewModel.exchangeStateLd) {
            binding.exchangeTv.isEnabled = it
        }
        observeData(viewModel.willReceiveLd) {
            binding.exchangeAmountTv.text = it
        }
        observeData(viewModel.historyNavigationLd) {
            findNavController().navigate(
                CurrencyCalculatorFragmentDirections.historyNavigation()
            )
        }
        observeData(viewModel.savedAction) {
            Toast.makeText(requireContext(), "Saved to the history", Toast.LENGTH_LONG).show()
        }
    }

    private fun showDatePicker(model: CalendarDataUi) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            dateListener,
            model.currentYear,
            model.currentMonth,
            model.currentDay
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

}