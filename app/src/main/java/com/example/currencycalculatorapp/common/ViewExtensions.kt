package com.example.currencycalculatorapp.common

import android.os.SystemClock
import android.view.View

fun View.clickWithDebounce(debounceTime: Long = 700L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime = 0L
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                return
            } else {
                action()
            }
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}