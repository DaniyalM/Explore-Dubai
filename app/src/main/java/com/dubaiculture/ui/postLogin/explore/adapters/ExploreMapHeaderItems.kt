package com.dubaiculture.ui.postLogin.explore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.dubaiculture.databinding.AttractionTitleListItemBinding

class ExploreMapHeaderItems() :
    ListAdapter<AttractionCategory, ExploreMapHeaderItems.MapViewHolder>(AttractionItemDiffCallback()) {
    private var previousSelection: AttractionCategory? = null
    fun setPreviousSelection(attractionCategory: AttractionCategory?) {
        previousSelection = attractionCategory
    }

    inner class MapViewHolder(val binding: AttractionTitleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(attractionCategory: AttractionCategory, position: Int) {
            binding.tvTitle.text = attractionCategory.title


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
        return MapViewHolder(
            AttractionTitleListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}

class AttractionItemDiffCallback : DiffUtil.ItemCallback<AttractionCategory>() {
    override fun areItemsTheSame(
        oldItem: AttractionCategory,
        newItem: AttractionCategory
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: AttractionCategory,
        newItem: AttractionCategory
    ): Boolean =
        oldItem == newItem
}

interface AttractionClickListener {
    fun onClick(attractionCategory: AttractionCategory)
}

