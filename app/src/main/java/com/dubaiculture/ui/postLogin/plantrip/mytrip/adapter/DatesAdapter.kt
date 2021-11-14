package com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.Duration
import com.dubaiculture.databinding.ItemTripDateBinding
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.DateClickListener


class DatesAdapter(val rowClickListener: DateClickListener) :
    ListAdapter<Duration, DatesAdapter.DateViewHolder>(DatesAdapter.DateDiffCallback()) {

    inner class DateViewHolder(
        val binding: ItemTripDateBinding,
        val rowClickListener: DateClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Duration) {
            binding.tvDate.text = date.dayDate.substring(0,2)
            when (date.isSelected) {
                true -> {
                    binding.cvFirst.setCardBackgroundColor(ContextCompat.getColor(binding.root.context,R.color.purple_650))
                    binding.tvDate.setTextColor(ContextCompat.getColor(binding.root.context,R.color.white_900))
                }
                false ->{
                    binding.cvFirst.setCardBackgroundColor(ContextCompat.getColor(binding.root.context,android.R.color.white))
                    binding.tvDate.setTextColor(ContextCompat.getColor(binding.root.context,android.R.color.black))


                }
            }
            binding.cvFirst.setOnClickListener {
                rowClickListener.rowClickListener(date)
                rowClickListener.rowClickListener(date, absoluteAdapterPosition)
            }
        }
    }

    class DateDiffCallback : DiffUtil.ItemCallback<Duration>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        return DateViewHolder(
            ItemTripDateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}