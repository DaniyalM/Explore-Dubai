package com.dubaiculture.ui.components.customEditText

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import com.dubaiculture.R
import com.dubaiculture.ui.components.customtextview.FontStyle

open class CustomEditText : AppCompatEditText {
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
            context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        for (i in 0..typedArray.indexCount) {
            when (val attr = typedArray.getIndex(i)) {
                R.styleable.CustomEditText_textTypeFont -> {
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