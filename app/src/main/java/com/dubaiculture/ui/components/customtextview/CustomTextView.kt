package com.dubaiculture.ui.components.customtextview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.dubaiculture.R

class CustomTextView : AppCompatTextView {
    private val defaultFontStyle = FontStyle.REGULAR

    constructor(context: Context) : super(context) {
        setupTypedAttrs(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setupTypedAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, style: Int) : super(context, attrs, style) {
        setupTypedAttrs(attrs)
    }

    fun setupTypedAttrs(attrs: AttributeSet?) {
        var selectedFontStyle = defaultFontStyle
        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        //loop for future attributes addition
        for (i in 0..typedArray.indexCount) {
            when (val attr = typedArray.getIndex(i)) {
                R.styleable.CustomTextView_textFont -> {
                    selectedFontStyle =
                        FontStyle.fromId(typedArray.getInt(attr, defaultFontStyle.id))
                }
            }
        }
        typedArray.recycle()
        setupFont(selectedFontStyle)


    }

    private fun setupFont(selectedFontStyle: FontStyle) {
        val typeface = when (selectedFontStyle) {
            FontStyle.LIGHT ->
                ResourcesCompat.getFont(context, R.font.neosansarabiclight)

            FontStyle.REGULAR ->
                ResourcesCompat.getFont(context, R.font.neo_sans_arabic_regular)

            FontStyle.BOLD ->
                ResourcesCompat.getFont(context, R.font.neosansarabicbold)
        }

        setTypeface(typeface)
    }

}