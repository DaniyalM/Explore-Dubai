package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.databinding.AttractionTitleListItemBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionHeaderClick
import com.app.dubaiculture.utils.AppConfigUtils
import com.app.dubaiculture.utils.glideInstance
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.attraction_title_list_item.view.*

class ExploreMapHeaderItems() : ListAdapter<AttractionCategory, ExploreMapHeaderItems.MapViewHolder>(AttractionItemDiffCallback()) {
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
    override fun areItemsTheSame(oldItem: AttractionCategory, newItem: AttractionCategory): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AttractionCategory, newItem: AttractionCategory): Boolean =
        oldItem == newItem
}
interface AttractionClickListener {
    fun onClick(attractionCategory: AttractionCategory)
}

