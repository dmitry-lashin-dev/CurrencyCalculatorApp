package com.example.currencycalculatorapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.currencycalculator.R
import com.example.currencycalculator.databinding.FragmentExchangeHistoryBinding
import com.example.currencycalculatorapp.common.observeData
import com.example.currencycalculatorapp.presentation.base.BaseFragment
import com.example.currencycalculatorapp.presentation.fragment.adapter.HistoryAdapter

class ExchangeHistoryFragment : BaseFragment<FragmentExchangeHistoryBinding>() {

    private val viewModel: ExchangeHistoryViewModel by viewModels()
    private var adapter: HistoryAdapter? = null

    override fun inflateViewBinding(): FragmentExchangeHistoryBinding {
        return FragmentExchangeHistoryBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapterAndRecycler()
        viewModel.loadHistory()
        binding.topToolbar.setNavigationOnClickListener {
            findNavController().navigate(ExchangeHistoryFragmentDirections.back())
        }
        observeData(viewModel.historyLd) {
            binding.historyRv.isVisible = !it.second
            binding.emptyHistory.isVisible = it.second
            adapter?.submitList(it.first)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(ExchangeHistoryFragmentDirections.back())
        }
    }

    private fun setupAdapterAndRecycler() {
        adapter = HistoryAdapter(requireContext())
        binding.historyRv.adapter = adapter
        val horizontalDecorator =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
            horizontalDecorator.setDrawable(it)
            binding.historyRv.addItemDecoration(horizontalDecorator)
        }
    }
}