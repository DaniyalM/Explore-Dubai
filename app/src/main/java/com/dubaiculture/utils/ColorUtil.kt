package com.dubaiculture.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import androidx.core.content.ContentProviderCompat.requireContext
import com.dubaiculture.R

object ColorUtil {

     fun fetchColor(context: Context,resourceId:Int): Int {
        val typedValue = TypedValue()
        val a: TypedArray =
            context.obtainStyledAttributes(typedValue.data, intArrayOf(resourceId))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }

}