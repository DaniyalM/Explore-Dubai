package com.dubaiculture.ui.postLogin.latestnews.newsfilter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.news.local.NewsTags
import com.dubaiculture.databinding.ItemCategoryFilterBinding
import com.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners.NewsTagsClickListener
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show

class NewsTagsListAdapter(val newsTagsClickListener: NewsTagsClickListener) :
    ListAdapter<NewsTags, NewsTagsListAdapter.NewsTagsViewHolder>(NewsTagsDiffCallback()) {

    inner class NewsTagsViewHolder(
        val binding: ItemCategoryFilterBinding,
        val rowClickListener: NewsTagsClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categories: NewsTags) {
            binding.tvTitleCategory.text = categories.tag_title
            binding.tvTitleCategoryWhite.text = categories.tag_title
            switchColor(binding, categories)


            binding.rootItemSelection.setOnClickListener {
                categories.isSelected = !categories.isSelected
                newsTagsClickListener.onTagSelectListner(categories)
                switchColor(binding, categories)

            }

        }
    }

    private fun switchColor(binding: ItemCategoryFilterBinding, categories: NewsTags) {
        if (categories.isSelected) {
            binding.borderBg.setBackgroundResource(R.drawable.rounded_shape_area_exp_purple)
            binding.tvTitleCategory.hide()
            binding.tvTitleCategoryWhite.show()
        } else
            binding.borderBg.setBackgroundResource(R.drawable.rounded_shape_area_exp)
        binding.tvTitleCategory.show()
        binding.tvTitleCategoryWhite.hide()
    }

    class NewsTagsDiffCallback : DiffUtil.ItemCallback<NewsTags>() {
        override fun areItemsTheSame(
            oldItem: NewsTags,
            newItem: NewsTags
        ): Boolean =
            oldItem.tag_id == newItem.tag_id

        override fun areContentsTheSame(
            oldItem: NewsTags,
            newItem: NewsTags
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsTagsViewHolder(
        ItemCategoryFilterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
        rowClickListener = newsTagsClickListener

    )

    override fun onBindViewHolder(holder: NewsTagsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}