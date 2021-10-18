package com.app.dubaiculture.ui.postLogin.more.faqs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.local.FaqItem
import com.app.dubaiculture.databinding.ItemFaqsLayoutBinding
import com.app.dubaiculture.ui.postLogin.more.faqs.adapters.clicklisteners.FaqsItemClickListner

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
        fun bind(faq: FaqItem, position: Int) {
            binding.apply {
                tvQuestionNum.text = (position + 1).toString()
                tvQuestions.text = faq.question
                tvAnswer.text = faq.answer
                if (!faq.is_expanded) {
                    imgToggle.setImageResource(R.drawable.remove)
                    tvAnswer.visibility = View.VISIBLE
                } else {
                    imgToggle.setImageResource(R.drawable.plus)
                    tvAnswer.visibility = View.GONE
                }
                ll.setOnClickListener {
                    Toast.makeText(it.context,faq.id.toString(),Toast.LENGTH_SHORT).show()
                    faqsItemClickListner.onClickFaqItem(faq)
                }
            }
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
        getItem(position)?.let { holder.bind(it, position) }
    }
}