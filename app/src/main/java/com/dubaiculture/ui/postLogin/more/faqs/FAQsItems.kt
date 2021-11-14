package com.dubaiculture.ui.postLogin.more.faqs

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.more.local.FaqItem
import com.dubaiculture.databinding.ItemFaqsLayoutBinding
import com.xwray.groupie.databinding.BindableItem

class FAQsItems<T : ViewDataBinding>(
    val faqs: FaqItem,
    val resLayout: Int = R.layout.item_faqs_layout,
    val context: Context,
    val isArabic: Boolean,
    var isExpand : Boolean?= false,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemFaqsLayoutBinding -> {
                viewBinding.let {
//                    setAnimation(it.rootView,position,context)
                    if(position>=0 && position<9){
                        it.tvQuestionNum.text = "${0}"+(position).plus(1).toString()
                    }else{
                        it.tvQuestionNum.text = (position+1).toString()
                    }
                    it.tvQuestions.text = faqs.question
                    it.imgToggle.setImageResource(R.drawable.plus)
                    it.rootView.setOnClickListener { items ->

//                        it.tvQuestions.setTextColor(context.getColorFromAttr(R.attr.colorOnSecondary))
//                        it.tvQuestionNum.setTextColorRes(R.color.gray_750)
                        if (isExpand == false) {
                            isExpand = true
                            it.imgToggle.setImageResource(R.drawable.remove)
                            it.tvAnswer.visibility = View.VISIBLE
                            it.tvAnswer.text = faqs.answer
                        } else {
                            isExpand = false
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

    private fun setAnimation(viewToAnimate: View, position: Int,context:Context) {
        // If the bound view wasn't previously displayed on screen, it's animated
        val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
        viewToAnimate.startAnimation(animation)
    }

}