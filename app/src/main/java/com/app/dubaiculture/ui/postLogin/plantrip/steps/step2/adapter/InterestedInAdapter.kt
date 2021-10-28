package com.app.dubaiculture.ui.postLogin.plantrip.steps.step2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.InterestedInType
import com.app.dubaiculture.databinding.ItemTripStep2Binding
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step2.adapter.clicklisteners.InterestedInClickListener

class InterestedInAdapter(val rowClickListener: InterestedInClickListener) :
    ListAdapter<InterestedInType, InterestedInAdapter.InterestedInViewHolder>(InterestedInAdapter.InterestedInDiffCallback()) {

    inner class InterestedInViewHolder(
        val binding: ItemTripStep2Binding,
        val rowClickListener: InterestedInClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(interestedIn: InterestedInType) {
            binding.interestedIn = interestedIn

            when (interestedIn.checked) {
                true -> {
                    binding.cvUser.strokeColor =
                        ContextCompat.getColor(binding.root.context, R.color.purple_650)
                }
                false -> {
                    binding.cvUser.strokeColor =
                        ContextCompat.getColor(binding.root.context, R.color.transparent)
                }
            }
            binding.cvUser.setOnClickListener {
                rowClickListener.rowClickListener(interestedIn)
                rowClickListener.rowClickListener(interestedIn, absoluteAdapterPosition)
            }
        }
    }

    class InterestedInDiffCallback : DiffUtil.ItemCallback<InterestedInType>() {
        override fun areItemsTheSame(
            oldItem: InterestedInType,
            newItem: InterestedInType
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: InterestedInType,
            newItem: InterestedInType
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestedInViewHolder {
        return InterestedInViewHolder(
            ItemTripStep2Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: InterestedInViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}