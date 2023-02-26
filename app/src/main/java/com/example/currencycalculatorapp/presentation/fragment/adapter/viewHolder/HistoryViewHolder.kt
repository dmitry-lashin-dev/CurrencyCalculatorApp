package com.example.currencycalculatorapp.presentation.fragment.adapter.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.currencycalculator.databinding.ItemHistoryBinding
import com.example.currencycalculatorapp.data.models.dto.ExchangeHistoryDto

class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemHistoryBinding.bind(itemView)

    fun bind(model: ExchangeHistoryDto) {
        binding.originDataTv.text = model.originAmount
        binding.targetDataTv.text = model.targetAmount
    }

}