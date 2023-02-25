package com.example.currencycalculatorapp.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observeData(data: LiveData<T>, action: (T) -> Unit) = apply {
    data.observe(this, Observer(action))
}