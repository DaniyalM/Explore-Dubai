package com.app.dubaiculture.ui.postLogin.survey.adapter

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.survey.request.Items
import com.app.dubaiculture.databinding.RowFillOutSurveyLayoutBinding
import com.app.dubaiculture.utils.hide
import com.app.dubaiculture.utils.show
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
                    when(itemForm.input){
                        "YesNo"->{
                            it.radioGroupYesNo.show()
                            it.commentsCardView.hide()
                            it.ratingStar.hide()

                        }
                        "Textbox"->{
                            it.commentsCardView.show()
                            it.ratingStar.hide()
                            it.radioGroupYesNo.hide()

                        }
                        "Rating" ->{
                            it.ratingStar.show()
                            it.commentsCardView.hide()
                            it.radioGroupYesNo.hide()
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