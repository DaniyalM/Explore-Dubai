package com.dubaiculture.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class OnlyVerticalSwipeRefreshLayout(context: Context?, attrs: AttributeSet?) : SwipeRefreshLayout(
    context!!, attrs
) {
    private val touchSlop: Int
    private var prevX = 0f
    private var declined = false
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                prevX = MotionEvent.obtain(event).x
                declined = false // New action
            }
            MotionEvent.ACTION_MOVE -> {
                val eventX = event.x
                val xDiff = Math.abs(eventX - prevX)
                if (declined || xDiff > touchSlop) {
                    declined = true // Memorize
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }
}