package com.app.dubaiculture.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.asynclayoutinflater.view.AsyncLayoutInflater

open class AsyncCell(context: Context,width:Int= MATCH_PARENT) : FrameLayout(context, null, 0, 0) {
    init {
        layoutParams = LayoutParams(width, WRAP_CONTENT)
    }

    open val layoutId = -1
    private var isInflated = false
    private val bindingFunctions: MutableList<AsyncCell.() -> Unit> = mutableListOf()


    fun inflate() {
        AsyncLayoutInflater(context).inflate(layoutId, this) { view, _, _ ->
            isInflated = true
            addView(createDataBindingView(view))
            bindView()
        }
    }

    private fun bindView() {
        with(bindingFunctions) {
            forEach { it() }
            clear()
        }
    }

    fun bindWhenInflated(bindFunc: AsyncCell.() -> Unit) {
        if (isInflated) {
            bindFunc()
        } else {
            bindingFunctions.add(bindFunc)
        }
    }

    open fun createDataBindingView(view: View): View? = view // override for usage with dataBinding

}