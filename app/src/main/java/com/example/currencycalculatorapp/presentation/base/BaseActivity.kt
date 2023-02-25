package com.example.currencycalculatorapp.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _viewBinding: VB? = null
    protected val binding: VB get() = _viewBinding!!

    abstract fun inflateViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = inflateViewBinding()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        _viewBinding = null
        super.onDestroy()
    }
}