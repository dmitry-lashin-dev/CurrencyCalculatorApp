package com.example.currencycalculatorapp.common

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

fun <T> LifecycleOwner.observeData(data: LiveData<T>, action: (T) -> Unit) = apply {
    data.observe(this, Observer(action))
}
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    /**
     *  Used for cases where T is Void, to make calls cleaner. Can be used only on the Main thread.
     */
    @MainThread
    fun call() {
        value = null
    }

    /**
     * Used for cases where T is Void, to make calls cleaner. Can be used on a non Main thread.
     */
    fun callSafe() {
        postValue(null)
    }
}