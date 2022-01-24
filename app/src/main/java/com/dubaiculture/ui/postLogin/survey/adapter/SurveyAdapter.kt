package com.dubaiculture.ui.postLogin.survey.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.survey.request.Items
import com.dubaiculture.databinding.RowFillOutSurveyLayoutBinding
import com.dubaiculture.ui.postLogin.survey.FieldType
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show
import com.xwray.groupie.databinding.BindableItem

class SurveyAdapter<T : ViewDataBinding>(
    val itemForm: Items,
    val resLayout: Int = R.layout.row_fill_out_survey_layout,
    val context: Context,
    val isArabic: Boolean,
    val callback: (input: String,inputType:FieldType) -> Unit,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is RowFillOutSurveyLayoutBinding -> {
                viewBinding.let {
                    setAnimation(it.rootView, position, context)
                    it.tvQuestions.text = "Q${position + 1} ${":"} ${itemForm.question}"
                    val type=FieldType.fromName(itemForm.input!!)
                    when (type) {
                        FieldType.YesNo -> {
                            it.radioGroupYesNo.show()
                            it.commentsCardView.hide()
                            it.ratingStar.hide()

                            it.radioGroupYesNo.setOnCheckedChangeListener { group, checkedId ->
                                when (checkedId) {
                                    R.id.rbYes -> {
                                        callback("Yes",type)
                                    }
                                    else -> callback("No",type)
                                }
                            }


                        }
                        FieldType.Textbox -> {
                            it.radioGroupYesNo.hide()
                            it.ratingStar.hide()
                            it.commentsCardView.show()
                            it.editComment.addTextChangedListener(object :TextWatcher{
                                override fun beforeTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    count: Int,
                                    after: Int
                                ) {

                                }

                                override fun onTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    before: Int,
                                    count: Int
                                ) {
                                    callback(s.toString().trim(),type)
                                }

                                override fun afterTextChanged(s: Editable?) {

                                }
                            })


                        }
                        FieldType.Rating -> {
                            it.radioGroupYesNo.hide()
                            it.commentsCardView.hide()
                            it.ratingStar.show()
                            it.ratingStar.setOnClickListener {view->
                                callback(it.ratingStar.rating.toString(),type)
                            }
                        }
                    }

                }
            }
        }
    }

    override fun getLayout() = resLayout

    private fun setAnimation(viewToAnimate: View, position: Int, context: Context) {
        // If the bound view wasn't previously displayed on screen, it's animated
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        viewToAnimate.startAnimation(animation)
    }
}