package com.example.currencycalculatorapp.presentation.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.currencycalculator.R
import com.example.currencycalculatorapp.data.models.dto.ExchangeHistoryDto
import com.example.currencycalculatorapp.presentation.fragment.adapter.viewHolder.HistoryViewHolder

class HistoryAdapter(context: Context) :
    ListAdapter<ExchangeHistoryDto, HistoryViewHolder>(HistoryDiff) {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(inflater.inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object HistoryDiff : DiffUtil.ItemCallback<ExchangeHistoryDto>() {
        override fun areItemsTheSame(
            oldItem: ExchangeHistoryDto,
            newItem: ExchangeHistoryDto
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ExchangeHistoryDto,
            newItem: ExchangeHistoryDto
        ): Boolean {
            return oldItem == newItem
        }
    }

}