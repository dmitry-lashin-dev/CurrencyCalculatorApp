package com.example.currencycalculatorapp.presentation.dialog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.currencycalculator.R
import com.example.currencycalculatorapp.domain.models.presentation.CurrencyDataUi
import com.example.currencycalculatorapp.presentation.dialog.adapter.viewHolder.CurrencyViewHolder

class CurrencyAdapter(context: Context) :
    ListAdapter<CurrencyDataUi, CurrencyViewHolder>(CurrencyDiffUtil) {

    private val inflater = LayoutInflater.from(context)
    var selectCurrencyListener: ((model: CurrencyDataUi) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(inflater.inflate(R.layout.item_currency, parent, false)) {
            selectCurrencyListener?.invoke(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyDataUi>() {
        override fun areItemsTheSame(oldItem: CurrencyDataUi, newItem: CurrencyDataUi): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CurrencyDataUi, newItem: CurrencyDataUi): Boolean {
            return oldItem == newItem
        }
    }

}