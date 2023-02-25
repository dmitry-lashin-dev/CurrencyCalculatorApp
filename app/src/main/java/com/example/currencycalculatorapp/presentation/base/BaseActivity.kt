package com.example.currencycalculatorapp.presentation.base

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.currencycalculatorapp.common.observeData
import com.example.currencycalculatorapp.presentation.dialog.DialogFactory

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _viewBinding: VB? = null
    protected val binding: VB get() = _viewBinding!!
    private var singleDialog: Dialog? = null

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


    fun handleProgressState(viewModel: BaseViewModel?) {
        viewModel?.apply {
            observeData(progressLd) {
                if (it) showGlobalProgress() else hideGlobalProgress()
            }
        }
    }

    private fun showGlobalProgress() {
        if (singleDialog == null) singleDialog = DialogFactory.createProgressDialog(this)
        if (singleDialog?.isShowing == false) singleDialog?.show()
    }

    private fun hideGlobalProgress() {
        singleDialog?.let {
            if (it.isShowing) it.dismiss()
        }
    }
}