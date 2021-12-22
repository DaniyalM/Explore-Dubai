package com.dubaiculture.ui.postLogin.plantrip.steps.step3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.LocationNearest
import com.dubaiculture.databinding.ItemTripStep3Binding
import com.dubaiculture.ui.postLogin.plantrip.steps.step3.adapter.clicklisteners.NearestLocationClickListener
import com.dubaiculture.utils.ColorUtil

class NearestLocationAdapter(val rowClickListener: NearestLocationClickListener) :
    ListAdapter<LocationNearest, NearestLocationAdapter.NearestLocationViewHolder>(
        NearestLocationAdapter.NearestLocationDiffCallback()
    ) {

    inner class NearestLocationViewHolder(
        val binding: ItemTripStep3Binding,
        val rowClickListener: NearestLocationClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(locationNearest: LocationNearest) {

            binding.nearestLocation = locationNearest
            binding.chipLocation.setTextColor(
                ColorUtil.fetchColor(binding.root.context,R.attr.color_light_theme_dark_theme_dark_gray)
            )
            if (locationNearest.isChecked) {
                binding.chipLocation.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white_900
                    )
                )
            } 

            binding.chipLocation.setOnClickListener {
                rowClickListener.rowClickListener(locationNearest)
                rowClickListener.rowClickListener(locationNearest, absoluteAdapterPosition)
            }
        }
    }

    class NearestLocationDiffCallback : DiffUtil.ItemCallback<LocationNearest>() {
        override fun areItemsTheSame(
            oldItem: LocationNearest,
            newItem: LocationNearest
        ): Boolean =
            oldItem.locationId == newItem.locationId

        override fun areContentsTheSame(
            oldItem: LocationNearest,
            newItem: LocationNearest
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearestLocationViewHolder {
        return NearestLocationViewHolder(
            ItemTripStep3Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: NearestLocationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}
   