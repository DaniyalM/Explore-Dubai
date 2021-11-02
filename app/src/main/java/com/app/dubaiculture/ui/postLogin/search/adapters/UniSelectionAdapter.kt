package com.app.dubaiculture.ui.postLogin.search.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.search.local.SearchTab
import com.app.dubaiculture.databinding.ExploreMapLayoutHeadersBinding
import com.app.dubaiculture.ui.postLogin.search.SearchFragment.Companion.selectedPosition
import com.app.dubaiculture.utils.getColorFromAttr
import com.app.dubaiculture.utils.hide

class UniSelectionAdapter<T>(val headerSelector: HeaderSelector) :
    ListAdapter<T, UniSelectionAdapter<T>.UniSelectionListViewHolder>
        (object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: T,
            newItem: T
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }) {


    inner class UniSelectionListViewHolder(
        val binding: ExploreMapLayoutHeadersBinding,
        val headerSelector: HeaderSelector
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: T) {
            if (header is SearchTab) {
                binding.apply {

                    imgInnerIcon.hide()
                    tvTitle.text = header.title
                    if (header.isSelected) {
                        selectedPosition = header.id
                        cardview.setCardBackgroundColor(Color.parseColor("#5d2c83"))
                        tvTitle.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.white_900
                            )
                        )
                    } else {
                        cardview.setCardBackgroundColor(binding.root.context.getColorFromAttr(R.attr.colorSurface))
                        tvTitle.setTextColor(
                            binding.root.context.getColorFromAttr(R.attr.colorSecondary)
                        )
                    }
                    cardview.setOnClickListener {
                        headerSelector.onHeaderSelection(header)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UniSelectionListViewHolder(
            ExploreMapLayoutHeadersBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            headerSelector
        )

    override fun onBindViewHolder(holder: UniSelectionListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    interface HeaderSelector {
        fun onHeaderSelection(tab: SearchTab)
    }

}