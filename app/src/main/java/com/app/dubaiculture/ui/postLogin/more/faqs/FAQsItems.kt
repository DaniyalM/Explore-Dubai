package com.app.dubaiculture.ui.postLogin.more.faqs

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.local.FAQs
import com.app.dubaiculture.data.repository.more.local.FaqItem
import com.app.dubaiculture.databinding.ItemFaqsLayoutBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

class FAQsItems<T : ViewDataBinding>(
    val faqs: FaqItem,
    val resLayout: Int = R.layout.item_faqs_layout,
    val context: Context,
    val isArabic: Boolean,
    var isExpand : Boolean?= false,
    var incrementQuestions : Int = 0
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemFaqsLayoutBinding -> {
                viewBinding.let {
                        it.tvQuestions.text = faqs.question
//                    it.tvQuestionNum.setText(incrementQuestions++.toString())
                    it.imgToggle.setImageResource(R.drawable.plus)
                    it.rootView.setOnClickListener {items->
                        if(isExpand==false){
                            isExpand= true
                            it.imgToggle.setImageResource(R.drawable.remove)
                            it.tvAnswer.visibility = View.VISIBLE
                                it.tvAnswer.text = faqs.answer
                        }else{
                            isExpand= false
                            it.imgToggle.setImageResource(R.drawable.plus)
                            it.tvAnswer.visibility = View.GONE
                        }

                    }

                }
            }
        }
    }

    override fun getLayout() = resLayout

    private fun arrowRTL(isArabic: Boolean, img: ImageView) {
        when {
            isArabic -> {
                img.scaleX = -1f

            }
            else -> {
                img.scaleX = 1f
            }
        }
    }

}