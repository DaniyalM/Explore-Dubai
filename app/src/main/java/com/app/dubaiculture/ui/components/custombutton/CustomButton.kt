package com.app.dubaiculture.ui.components.custombutton

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.components.customtextview.FontStyle


class CustomButton(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    private val defaultFontStyle = FontStyle.REGULAR
    private var defaultButtonType: ButtonType = ButtonType.FILLED

    private var selectedButtonType = defaultButtonType

    init {
        var selectedFontStyle = defaultFontStyle

        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
        //loop for future attributes addition
        for (i in 0..typedArray.indexCount) {
            when (val attr = typedArray.getIndex(i)) {
                R.styleable.CustomButton_cb_textFont -> {
                    selectedFontStyle =
                        FontStyle.fromId(typedArray.getInt(attr, defaultFontStyle.id))
                }
                R.styleable.CustomButton_cb_type -> {
                    selectedButtonType =
                        ButtonType.fromId(typedArray.getInt(attr, defaultFontStyle.id))
                }
            }
        }
        setupFont(selectedFontStyle)
        typedArray.recycle()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (selectedButtonType == ButtonType.FILLED) {
            background = if (enabled) {
                ContextCompat.getDrawable(context, R.drawable.btn_register)
            } else {
                ContextCompat.getDrawable(context, R.drawable.bg_btn_filled_disabled)
            }
        }

//        else if (selectedButtonType == ButtonType.OUTLINED) {
//            background = if (enabled) {
//                ContextCompat.getDrawable(context, R.drawable.bg_btn_outlined)
//            } else {
//                ContextCompat.getDrawable(context, R.drawable.bg_btn_outlined_disabled)
//            }
            setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    if (enabled) {
                        R.color.white_900
                    } else {
                        R.color.black_200
                    },
                    null
                )

            )
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


