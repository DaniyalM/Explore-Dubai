package com.dubaiculture.ui.postLogin.survey

import android.graphics.Color
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import com.dubaiculture.R
import com.dubaiculture.data.repository.survey.request.Items
import com.dubaiculture.databinding.*
import com.dubaiculture.ui.components.customEditText.CustomEditText
import com.dubaiculture.utils.setColouredSpan
import com.idlestar.ratingstar.RatingStarView


object SurveyFieldUtils {

    fun createTextView(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        string: String
    ): TextView {
        val view = SurveyItemTextLayoutBinding.inflate(layoutInflater, root, false)
        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(10, 10, 10, 10)
        root.removeView(view.questionNum)
        val textView = view.questionNum
        val text = string
        textView.text = text
        textView.textSize = 12.5F
        textView.layoutParams = params
        textView.setColouredSpan("*", Color.RED)
        return textView
    }

    fun createEditText(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: Items
    ): CustomEditText {
        val view = EserviceEditTextBinding.inflate(layoutInflater, root, false)
        val editText = view.editText

        editText.id = fieldValueItem.index
        editText.hint = root.context.resources.getString(R.string.add_your_comment_here)
        editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE

        return editText
    }

    fun createCardForEditText(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: Items,
        count:Int
    ) {
        val view = SurvayEditTextItemLayoutBinding.inflate(layoutInflater, root, false)
        val childContainer = view.childContainer

        childContainer.addView(
            createTextView(
                layoutInflater,
                childContainer,
                "${fieldValueItem.index + 1}/ ${count} ${root.context.resources.getString(R.string.question)}"
            )
        )
        childContainer.addView(
            createTextView(
                layoutInflater,
                childContainer,
                "Q${fieldValueItem.index + 1}: ${fieldValueItem.question}"
            )
        )
        childContainer.addView(createEditText(layoutInflater, childContainer, fieldValueItem))
        root.addView(view.root)


    }

    fun createRadioFields(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: Items,
    ): RadioGroup {
        val view = SurveyRadioItemLayoutBindingImpl.inflate(layoutInflater, root, false)
        val radio = view.radioGroupYesNo

        radio.id = fieldValueItem.index
        return radio
    }

    fun createCardForRadioButtons(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: Items,
        count:Int) {
        val view = SurvayEditTextItemLayoutBinding.inflate(layoutInflater, root, false)
        val childContainer = view.childContainer

        childContainer.addView(
            createTextView(
                layoutInflater,
                childContainer,
                "${fieldValueItem.index + 1}/ ${count} ${root.context.resources.getString(R.string.question)}"
            )
        )
        childContainer.addView(
            createTextView(
                layoutInflater,
                childContainer,
                "Q${fieldValueItem.index + 1}: ${fieldValueItem.question}"
            )
        )
        childContainer.addView(
            createRadioFields(
                layoutInflater,
                childContainer,
                fieldValueItem
            )
        )
        root.addView(view.root)


    }

    fun createRatingField(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: Items,
    ): RatingStarView {
        val view = SurveryRatingItemLayoutBinding.inflate(layoutInflater, root, false)
        val rating = view.ratingStar
        rating.id = fieldValueItem.index
        return rating
    }

    fun createCardForRatingField(
        layoutInflater: LayoutInflater,
        root: ViewGroup,
        fieldValueItem: Items,
        count:Int
    ) {
        val view = SurvayEditTextItemLayoutBinding.inflate(layoutInflater, root, false)
        val childContainer = view.childContainer

        childContainer.addView(
            createTextView(
                layoutInflater,
                childContainer,
                "${fieldValueItem.index + 1}/ ${count} ${root.context.resources.getString(R.string.question)}"
            )
        )
        childContainer.addView(
            createTextView(
                layoutInflater,
                childContainer,
                "Q${fieldValueItem.index + 1}: ${fieldValueItem.question}"
            )
        )
        childContainer.addView(
            createRatingField(
                layoutInflater,
                childContainer,
                fieldValueItem
            )
        )
        root.addView(view.root)


    }
}