package com.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.Duration
import com.dubaiculture.databinding.ItemDurationSummaryBinding
import com.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners.DurationClickListener
import java.text.NumberFormat
import java.util.*

class DurationSummaryAdapter(
    val rowClickListener: DurationClickListener, val currentLanguage: Locale
) :
    ListAdapter<Duration, DurationSummaryAdapter.DurationViewHolder>(
        DurationSummaryAdapter.DurationDiffCallback()
    ) {

    inner class DurationViewHolder(
        val binding: ItemDurationSummaryBinding,
        val rowClickListener: DurationClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(duration: Duration) {

            binding.data = duration
            if (duration.hour != binding.root.context.getString(R.string.select_hour)) {
                val separated: List<String> = duration.hour.split(" ")
                if (currentLanguage != Locale.ENGLISH) {
                    var nf: NumberFormat = NumberFormat.getInstance(Locale("ar"))
//                    val dayCount = Integer.parseInt(day.duration.substring(0, day.duration.indexOf(" ")))
                    binding.tvDuration.text =
                        nf.format(Integer.parseInt(separated[0])) + " " + separated[1]
                } else {
                    var nf: NumberFormat = NumberFormat.getInstance(Locale("en"))
                    binding.tvDuration.text =
                        nf.format(Integer.parseInt(separated[0])) + " " + separated[1]
                }
            } else {
                binding.tvDuration.text = duration.hour
            }
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