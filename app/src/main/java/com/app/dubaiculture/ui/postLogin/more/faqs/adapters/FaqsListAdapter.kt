package com.app.dubaiculture.ui.postLogin.more.faqs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.local.FaqItem
import com.app.dubaiculture.databinding.ItemFaqsLayoutBinding
import com.app.dubaiculture.ui.postLogin.more.faqs.adapters.clicklisteners.FaqsItemClickListner
import com.app.dubaiculture.utils.getColorFromAttr

class FaqsListAdapter(val faqsItemClickListner: FaqsItemClickListner) :
    ListAdapter<FaqItem, FaqsListAdapter.FaqsViewHolder>
        (object : DiffUtil.ItemCallback<FaqItem>() {
        override fun areItemsTheSame(oldItem: FaqItem, newItem: FaqItem) =
            oldItem.question == newItem.question

        override fun areContentsTheSame(oldItem: FaqItem, newItem: FaqItem) =
            oldItem.hashCode() == newItem.hashCode()
    }) {

    inner class FaqsViewHolder(
        val binding: ItemFaqsLayoutBinding,
        val faqsItemClickListner: FaqsItemClickListner
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(faq: FaqItem) {
            binding.apply {
                setAnimation(binding.rootView, binding.root.context)
                tvQuestionNum.text = faq.id.toString()
                tvQuestions.text = faq.question
                tvAnswer.text = faq.answer
                tvQuestions.setTextColor(binding.root.context.getColorFromAttr(R.attr.colorSecondary))
                tvAnswer.setTextColor(binding.root.context.getColorFromAttr(R.attr.colorSecondary))
                tvQuestionNum.setTextColor(binding.root.context.getColorFromAttr(R.attr.colorSecondary))
                if (faq.is_expanded) {
                    tvQuestionNum.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.purple_600
                        )
                    )
                    imgToggle.setImageResource(R.drawable.remove_purple)
                    tvQuestions.setTextColor(binding.root.context.getColorFromAttr(R.attr.color_dubai_logo))
                    tvAnswer.setTextColor(binding.root.context.getColorFromAttr(R.attr.color_dubai_logo))
                    tvAnswer.visibility = View.VISIBLE
                } else {
                    imgToggle.setImageResource(R.drawable.plus)
                    tvAnswer.visibility = View.GONE
                }
                rootView.setOnClickListener {
                    faqsItemClickListner.onClickFaqItem(faq)
                }
            }
        }

        private fun setAnimation(viewToAnimate: View, context: Context) {
            // If the bound view wasn't previously displayed on screen, it's animated
            val animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
            viewToAnimate.startAnimation(animation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqsViewHolder {
        return FaqsViewHolder(
            ItemFaqsLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            faqsItemClickListner
        )
    }

    override fun onBindViewHolder(holder: FaqsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


}