package com.app.dubaiculture.ui.components.searchview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.app.dubaiculture.R

class CustomSearchView(context: Context, attars: AttributeSet?) : FrameLayout(context, attars) {
    init {
        val view= inflate(context, R.layout.toolbar_layout,null)
        addView(view)
    }
}