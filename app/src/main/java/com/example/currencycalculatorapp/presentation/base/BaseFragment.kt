package com.example.currencycalculatorapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding!!

    abstract fun inflateViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateViewBinding()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun getBaseActivity(): BaseActivity<*>? {
        return if (activity is BaseActivity<*>) activity as BaseActivity<*> else null
    }

    protected fun handleProgressState(viewModel: BaseViewModel?) {
        getBaseActivity()?.handleProgressState(viewModel)
    }
}