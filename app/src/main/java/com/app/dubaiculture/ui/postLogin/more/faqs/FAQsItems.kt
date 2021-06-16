package com.app.dubaiculture.ui.postLogin.more.faqs

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.local.FAQs
import com.app.dubaiculture.data.repository.more.local.FaqItem
import com.app.dubaiculture.databinding.ItemFaqsLayoutBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.setTextColorRes
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
                    setAnimation(it.rootView,position,context)
                    if(position>=0 && position<9){
                        it.tvQuestionNum.text = "${0}"+(position).plus(1).toString()
                    }else{
                        it.tvQuestionNum.text = (position+1).toString()
                    }
                    it.tvQuestions.text = faqs.question
                    it.imgToggle.setImageResource(R.drawable.plus)
                    it.ll.setOnClickListener {items->
                        if(isExpand==false){
                            isExpand= true
                            it.tvQuestionNum.setTextColorRes(R.color.purple_900)
                            it.tvQuestions.setTextColorRes(R.color.purple_900)

                            it.imgToggle.setImageResource(R.drawable.remove)
                            it.tvAnswer.visibility = View.VISIBLE
                                it.tvAnswer.text = faqs.answer
                        }else{
                            isExpand= false
                            it.tvQuestions.setTextColorRes(R.color.black_200)
                            it.tvQuestionNum.setTextColorRes(R.color.gray_750)
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