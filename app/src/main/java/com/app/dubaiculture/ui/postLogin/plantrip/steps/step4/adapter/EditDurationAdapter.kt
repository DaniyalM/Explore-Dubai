package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.Duration
import com.app.dubaiculture.databinding.ItemEditDurationBinding
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners.DurationClickListener

class EditDurationAdapter(val rowClickListener: DurationClickListener) :
    ListAdapter<Duration, EditDurationAdapter.DurationViewHolder>(
        EditDurationAdapter.DurationDiffCallback()
    ) {

    inner class DurationViewHolder(
        val binding: ItemEditDurationBinding,
        val rowClickListener: DurationClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(duration: Duration) {

            binding.data = duration
            binding.view = this
            when (duration.isDay) {
                0 -> {
                    binding.btnDay.icon =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_day)

                    binding.btnDay.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.transparent)
                    )

                    binding.btnNight.icon =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_night)

                    binding.btnNight.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.transparent)
                    )
                }
                1 -> {
                    binding.btnDay.icon =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_day_selected)

                    binding.btnDay.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.purple_650)
                    )

                    binding.btnNight.icon =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_night)

                    binding.btnNight.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.transparent)
                    )
                }
                2 -> {
                    binding.btnNight.icon =
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.ic_night_selected
                        )

                    binding.btnNight.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.purple_650)
                    )

                    binding.btnDay.icon =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_day)

                    binding.btnDay.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.transparent)
                    )
                }
            }

        }

        fun onDropDownClicked(view: View) {

            showMenu(view, R.menu.duration_menu)

        }

        fun showMenu(v: View, @MenuRes menuRes: Int) {
            val popup = PopupMenu(v.context, v)
            popup.menuInflater.inflate(menuRes, popup.menu)

            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                rowClickListener.rowClickListener(binding.data!!.copy(hour = menuItem.title.toString()))
                true
            }
            popup.setOnDismissListener {
                // Respond to popup being dismissed.
            }
            // Show the popup menu.
            popup.show()
        }

        fun onDaySelected(duration: Duration) {

            rowClickListener.rowClickListener(duration.copy(isDay = 1))
//        tripSharedViewModel.updateDurationList(duration.copy(isDay = 1))
        }

        fun onNightSelected(duration: Duration) {

            rowClickListener.rowClickListener(duration.copy(isDay = 2))
//        tripSharedViewModel.updateDurationList(duration.copy(isDay = 2))

        }

        fun onItemSelected(duration: Duration) {

            rowClickListener.rowClickListener(duration.copy(isSelected = !duration.isSelected))

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
            ItemEditDurationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: DurationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}