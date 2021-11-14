package com.dubaiculture.ui.components.customreadmore

import android.content.Context
import android.graphics.Color
import com.dubaiculture.utils.Constants

private const val CHAR_LENGTH = 90

fun getReadMoreOptions(
        context: Context,
        readMoreClickListener: ReadMoreClickListener,
        color: String = Constants.Colors.SEE_MORE_BLUE
): ReadMoreOption =
    ReadMoreOption.Builder(context)
        .textLength(CHAR_LENGTH, ReadMoreOption.TYPE_CHARACTER)
        .moreLabel("See more")
        .lessLabel("See less")
        .moreLabelColor(Color.parseColor(color))
        .lessLabelColor(Color.parseColor(color))
        .labelUnderLine(true)
        .expandAnimation(false)
        .listener(readMoreClickListener)
        .build()