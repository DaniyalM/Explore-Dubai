package com.dubaiculture.utils

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher

class EndTypingWatcher(
    var delayMillis: Long = DELAY,
    val action: () -> Unit

) : Handler(Looper.getMainLooper()), TextWatcher {
    companion object {
        private const val DELAY: Long = 1000
    }

    var lastEditTime: Long = 0

    private val finishCheckerRunnable = Runnable {
        if (System.currentTimeMillis() > lastEditTime + delayMillis - 500) {
            action.invoke()
        }
    }

    override fun afterTextChanged(s: Editable?) {
        afterTextChanged()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    private fun afterTextChanged() {
        lastEditTime = System.currentTimeMillis()
        postDelayed(finishCheckerRunnable, delayMillis)
    }

    private fun onTextChanged() {
        removeCallbacks(finishCheckerRunnable)
    }

}