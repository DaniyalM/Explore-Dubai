package com.dubaiculture.ui.postLogin.eServiceStatus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.dubaiculture.databinding.ItemEserviceStatusBinding

class EServiceStatusAdapter(
) : ListAdapter<BeaconItems, EServiceStatusAdapter.StatusListViewHolder>(
    BeaconComparator()
) {

    class BeaconComparator : DiffUtil.ItemCallback<BeaconItems>() {
        override fun areItemsTheSame(oldItem: BeaconItems, newItem: BeaconItems) =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: BeaconItems, newItem: BeaconItems) =
            oldItem.id == newItem.id

    }


    inner class StatusListViewHolder(
        val binding: ItemEserviceStatusBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(beaconItem: BeaconItems) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StatusListViewHolder(
            ItemEserviceStatusBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: StatusListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}
