package com.dubaiculture.ui.components.customautocompletetextview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.dubaiculture.R
import com.dubaiculture.ui.components.customtextview.FontStyle
import com.google.android.material.textfield.TextInputEditText

open class CustomTextInputEditText(context: Context, attributeSet: AttributeSet) :
        TextInputEditText(context, attributeSet) {
    private val defaultFontStyle = FontStyle.REGULAR

    init {
        var selectedFontStyle = defaultFontStyle
        val typedArray: TypedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.CustomTextInputLayout)
        for (i in 0..typedArray.indexCount) {
            when (val attr = typedArray.getIndex(i)) {
                R.styleable.CustomTextInputLayout_textTypeFace -> {
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