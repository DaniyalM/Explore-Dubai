package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionTitleListItemBinding

class ExploreMapHeaderItems() :
    ListAdapter<AttractionCategory, ExploreMapHeaderItems.MapViewHolder>(AttractionItemDiffCallback()) {
    private var previousSelection: AttractionCategory? = null

    fun setPreviousSelection(attractionCategory: AttractionCategory?) {
        previousSelection = attractionCategory
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

    inner class MapViewHolder(val binding: AttractionTitleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(attractionCategory: AttractionCategory, position: Int) {
            binding.tvTitle.text = attractionCategory.title
//            binding.imgInnerIcon.setImageResource()

//            binding.tvTitle.setBackgroundColor
//                ResourcesCompat.getColor(
//                    binding.root.resources,
//                    if (country.isSelected) {
//                        binding.ivSelected.visibility = View.VISIBLE
//                        R.color.country_selection
//                    } else {
//                        binding.ivSelected.visibility = View.GONE
//                        android.R.color.white
//                    },
//                    null
//                )
//            )

//            binding.tvTitle.setTextColor(
//                ResourcesCompat.getColor(
//                    binding.root.resources,
//                    if (country.isSelected) {
//                        android.R.color.white
//                    } else {
//                        android.R.color.black
//                    },
//                    null
//                )
//            )
            binding.attractions.setOnClickListener {
//                country.isSelected = true
//                countryClickListener.onClick(country)
//                notifyItemChanged(position)
//                previousSelection?.apply {
//                    if (previousSelection != country) {
//                        previousSelection?.isSelected = false
//                        notifyItemChanged(
//                            currentList.indexOf(previousSelection)
//                        )
//                    }
//                }
//                previousSelection = country
            }

//            Glide.with(binding.root.context)
//                .load("https://www.countryflags.io/${country.iso}/shiny/64.png")
//                .into(binding.ivCountry)

        }
    }


}

class AttractionItemDiffCallback : DiffUtil.ItemCallback<AttractionCategory>() {
    override fun areItemsTheSame(oldItem: AttractionCategory, newItem: AttractionCategory): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AttractionCategory, newItem: AttractionCategory): Boolean =
        oldItem == newItem

}