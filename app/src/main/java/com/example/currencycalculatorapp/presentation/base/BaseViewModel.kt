package com.example.currencycalculatorapp.presentation.base

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    companion object {
        const val COROUTINE_ERROR_TAG = "COROUTINE_ERROR_TAG"
    }

    /**
     * This is the job for all coroutines started by ViewModel
     */
    protected var viewModelJob = SupervisorJob()

    protected var exceptionHandler: ((Throwable) -> Unit)? = null

    /**
     * This is the main scope for all coroutines launched by ViewModel
     */
    protected val viewModelScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main + viewModelJob + CoroutineExceptionHandler { _, throwable ->
            Log.e(COROUTINE_ERROR_TAG, "BaseViewModel, view model scope error = $throwable")
            exceptionHandler?.invoke(throwable)
        })

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}