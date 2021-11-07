package com.dubaiculture.ui.postLogin.survey.adapter

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.survey.request.Items
import com.dubaiculture.databinding.RowFillOutSurveyLayoutBinding
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show
import com.xwray.groupie.databinding.BindableItem

class SurveyAdapter <T : ViewDataBinding>(
    val itemForm: Items,
    val resLayout: Int = R.layout.row_fill_out_survey_layout,
    val context: Context,
    val isArabic: Boolean,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is RowFillOutSurveyLayoutBinding -> {
                viewBinding.let {
                    setAnimation(it.rootView,position,context)
                    it.tvQuestions.text = "Q${position+1} ${":"} ${itemForm.question}"
                    when(itemForm.input){

                        "YesNo"->{
                            it.radioGroupYesNo.show()

                        }
                        "Textbox"->{
                            it.commentsCardView.show()

                        }
                        "Rating" ->{
                            it.ratingStar.show()
                        }
                    }

                }
            }
        }
    }

    override fun getLayout() = resLayout

    private fun setAnimation(viewToAnimate: View, position: Int, context:Context) {
        // If the bound view wasn't previously displayed on screen, it's animated
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        viewToAnimate.startAnimation(animation)
    }
}