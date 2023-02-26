package com.example.currencycalculatorapp.presentation.dialog.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.currencycalculator.databinding.ItemCurrencyBinding
import com.example.currencycalculatorapp.domain.models.presentation.CurrencyDataUi

class CurrencyViewHolder(
    view: View,
    itemClickListener: ((position: Int) -> Unit)?
) : RecyclerView.ViewHolder(view) {

    private val binding = ItemCurrencyBinding.bind(itemView)

    init {
        binding.root.setOnClickListener {
            itemClickListener?.invoke(adapterPosition)
        }
    }

    fun bind(model: CurrencyDataUi) {
        with(binding) {
            currencyNameTv.text = model.name
            currencyTickerTv.text = model.ticker
        }
    }
}