package com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.app.dubaiculture.databinding.ItemMyTripBinding

import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.MyTripClickListener
import com.app.dubaiculture.utils.Constants

class MyTripAdapter(val rowClickListener: MyTripClickListener) :
    ListAdapter<EventsAndAttraction, MyTripAdapter.MyTripViewHolder>(MyTripAdapter.MyTripDiffCallback()) {

    inner class MyTripViewHolder(
        val binding: ItemMyTripBinding,
        val rowClickListener: MyTripClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(eventAttraction: EventsAndAttraction) {

            binding.data = eventAttraction

            when(eventAttraction.travelMode){
                Constants.TRAVEL_MODE.DRIVING ->{
                    binding.ivCar.setBackgroundResource(R.drawable.ic_car_green)
                }
                Constants.TRAVEL_MODE.WALKING ->{
                    binding.ivCar.setBackgroundResource(R.drawable.ic_walking_green)

                }
                Constants.TRAVEL_MODE.TRANSIT ->{
                    binding.ivCar.setBackgroundResource(R.drawable.ic_train_green)

                }
                Constants.TRAVEL_MODE.BICYCLING ->{
                    binding.ivCar.setBackgroundResource(R.drawable.ic_bus_green)


                }
            }

            binding.llIcons.setOnClickListener {
                rowClickListener.rowClickListener(eventAttraction)

            }
            binding.cvNavigation.setOnClickListener {
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
        getItem(position)?.let { holder.bind(it) }
    }

}