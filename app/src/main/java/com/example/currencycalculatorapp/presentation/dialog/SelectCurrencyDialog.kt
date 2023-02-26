package com.example.currencycalculatorapp.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.currencycalculator.R
import com.example.currencycalculator.databinding.DialogSelectCurrencyBinding
import com.example.currencycalculatorapp.common.setStateExpandedAndHideable
import com.example.currencycalculatorapp.domain.models.presentation.CurrencyDataUi
import com.example.currencycalculatorapp.presentation.dialog.adapter.CurrencyAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectCurrencyDialog : BottomSheetDialogFragment() {

    companion object {
        private const val EXTRA_DATA_KEY = "arg1"

        fun getInstance(currencies: List<CurrencyDataUi>): SelectCurrencyDialog {
            return SelectCurrencyDialog().apply {
                arguments = bundleOf(EXTRA_DATA_KEY to currencies)
            }
        }
    }

    private var binding: DialogSelectCurrencyBinding? = null
    private var adapter: CurrencyAdapter? = null

    var selectCurrencyListener: ((model: CurrencyDataUi) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSelectCurrencyBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            (it as BottomSheetDialog).setStateExpandedAndHideable()
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapterAndRecycler()
        getData()
    }

    private fun setupAdapterAndRecycler() {
        adapter = CurrencyAdapter(requireContext())
        adapter?.selectCurrencyListener = {
            selectCurrencyListener?.invoke(it)
            dismiss()
        }
        val horizontalDecorator =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
            horizontalDecorator.setDrawable(it)
            binding?.currencyListRv?.addItemDecoration(horizontalDecorator)
        }
        binding?.currencyListRv?.adapter = adapter
    }

    private fun getData() {
        arguments?.let { bundle ->
            bundle.getParcelableArrayList<CurrencyDataUi>(EXTRA_DATA_KEY)?.let {
                adapter?.submitList(it)
            }
        }
    }
}