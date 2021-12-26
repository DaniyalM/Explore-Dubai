package com.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.Duration
import com.dubaiculture.data.repository.trip.local.Durations
import com.dubaiculture.databinding.ItemDurationBinding
import com.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners.DurationClickListener
import com.dubaiculture.utils.ColorUtil

class DurationAdapter(val rowClickListener: DurationClickListener,val addDurationList: Durations) :
    ListAdapter<Duration, DurationAdapter.DurationViewHolder>(
        DurationAdapter.DurationDiffCallback()
    ) {

    inner class DurationViewHolder(
        val binding: ItemDurationBinding,
        val rowClickListener: DurationClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        var states = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_pressed)
        )

        var colors = intArrayOf(
            Color.WHITE,
            Color.WHITE,
            Color.WHITE,
            Color.WHITE
        )

        var unSelectedStates = arrayOf(
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_pressed)
        )

        var unSelectedColors = intArrayOf(
            ColorUtil.fetchColor(binding.root.context,R.attr.colorSecondaryVariant),
            ColorUtil.fetchColor(binding.root.context,R.attr.colorSecondaryVariant),
            ColorUtil.fetchColor(binding.root.context,R.attr.colorSecondaryVariant),
            ColorUtil.fetchColor(binding.root.context,R.attr.colorSecondaryVariant),
        )

        fun bind(duration: Duration) {

            binding.data = duration
            binding.view = this
            when (duration.isDay) {
                0 -> {
                    binding.btnDay.icon = ContextCompat.getDrawable(binding.root.context, R.drawable.ic_day)

                    binding.btnDay.iconTint = ColorStateList(unSelectedStates, unSelectedColors)


                    binding.btnDay.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.transparent)
                    )

                    binding.btnNight.icon =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_night)

                    binding.btnNight.iconTint = ColorStateList(unSelectedStates, unSelectedColors)


                    binding.btnNight.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.transparent)
                    )
                }
                1 -> {
                    binding.btnDay.icon =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_day_selected)

                    binding.btnDay.iconTint = ColorStateList(states, colors)

                    binding.btnDay.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.purple_650)
                    )

                    binding.btnNight.icon =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_night)

                    binding.btnNight.iconTint = ColorStateList(unSelectedStates, unSelectedColors)

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

                    binding.btnNight.iconTint = ColorStateList(states, colors)

                    binding.btnNight.setBackgroundColor(
                        ContextCompat.getColor(binding.root.context, R.color.purple_650)
                    )

                    binding.btnDay.icon =
                        ContextCompat.getDrawable(binding.root.context, R.drawable.ic_day)

                    binding.btnDay.iconTint = ColorStateList(unSelectedStates, unSelectedColors)


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

            addDurationList.hoursList.forEach {
                popup.menu.add(it.duration)
            }


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
            ItemDurationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: DurationViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}