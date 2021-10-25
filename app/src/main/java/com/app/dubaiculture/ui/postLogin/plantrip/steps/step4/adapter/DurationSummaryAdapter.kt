package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.data.repository.trip.local.Duration
import com.app.dubaiculture.databinding.ItemDurationSummaryBinding
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners.DurationClickListener

class DurationSummaryAdapter(val rowClickListener: DurationClickListener) :
    ListAdapter<Duration, DurationSummaryAdapter.DurationViewHolder>(
        DurationSummaryAdapter.DurationDiffCallback()
    ) {

    inner class DurationViewHolder(
        val binding: ItemDurationSummaryBinding,
        val rowClickListener: DurationClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(duration: Duration) {

            binding.data = duration
//            binding.cvUser.setOnClickListener {
//                rowClickListener.rowClickListener(duration)
//                rowClickListener.rowClickListener(duration, absoluteAdapterPosition)
//            }
        }
    }

    class DurationDiffCallback : DiffUtil.ItemCallback<Duration>() {
        override fun areItemsTheSame(
            oldItem: Duration,
            newItem: Duration
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Duration,
            newItem: Duration
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DurationViewHolder {
        return DurationViewHolder(
            ItemDurationSummaryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: DurationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}