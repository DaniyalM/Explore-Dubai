package com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.dubaiculture.databinding.ItemMyTripBinding

import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.MyTripClickListener
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.hide
import java.util.*

class MyTripAdapter(val rowClickListener: MyTripClickListener, val currentLanguage: Locale) :
    ListAdapter<EventsAndAttraction, MyTripAdapter.MyTripViewHolder>(MyTripAdapter.MyTripDiffCallback()) {

    inner class MyTripViewHolder(
        val binding: ItemMyTripBinding,
        val rowClickListener: MyTripClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(eventAttraction: EventsAndAttraction, isLast: Boolean) {

            binding.data = eventAttraction
            backArrowRTL(binding.ivDirectionArrow)
            when (eventAttraction.travelMode) {
                Constants.TRAVEL_MODE.DRIVING -> {
                    binding.ivCar.setBackgroundResource(R.drawable.ic_car_green)
                }
                Constants.TRAVEL_MODE.WALKING -> {
                    binding.ivCar.setBackgroundResource(R.drawable.ic_walking_green)

                }
                Constants.TRAVEL_MODE.TRANSIT -> {
                    binding.ivCar.setBackgroundResource(R.drawable.ic_train_green)

                }
                Constants.TRAVEL_MODE.BICYCLING -> {
                    binding.ivCar.setBackgroundResource(R.drawable.ic_bus_green)


                }
            }

            if (isLast) {
                binding.belowDotted.hide()
            }

            binding.llIcons.setOnClickListener {
                rowClickListener.rowClickListener(eventAttraction)

            }


            binding.cvNavigation.setOnClickListener {
                rowClickListener.rowClickListenerDirections(eventAttraction.latitude,eventAttraction.longitude)
//                rowClickListener.rowClickListener(eventAttraction)
//                rowClickListener.rowClickListener(eventAttraction, absoluteAdapterPosition)
            }
        }
    }

    class MyTripDiffCallback : DiffUtil.ItemCallback<EventsAndAttraction>() {
        override fun areItemsTheSame(
            oldItem: EventsAndAttraction,
            newItem: EventsAndAttraction
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: EventsAndAttraction,
            newItem: EventsAndAttraction
        ): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTripViewHolder {
        return MyTripViewHolder(
            ItemMyTripBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            rowClickListener
        )
    }

    override fun onBindViewHolder(holder: MyTripViewHolder, position: Int) {

        getItem(position)?.let { holder.bind(it, (itemCount - 1 == position)) }
    }

    fun backArrowRTL(img: ImageView) {

        if (currentLanguage != Locale.ENGLISH) {
            img.rotation = -180f
        }

    }

}