package com.app.dubaiculture.ui.postLogin.plantrip.steps.step3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.data.repository.trip.local.LocationNearest
import com.app.dubaiculture.databinding.ItemTripStep3Binding
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step3.adapter.clicklisteners.NearestLocationClickListener

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
   