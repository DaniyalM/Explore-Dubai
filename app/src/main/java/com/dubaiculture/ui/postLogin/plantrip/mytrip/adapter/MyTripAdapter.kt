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
import com.dubaiculture.utils.show
import java.text.NumberFormat
import java.util.*

class MyTripAdapter(val rowClickListener: MyTripClickListener, val currentLanguage: Locale) :
    ListAdapter<EventsAndAttraction, MyTripAdapter.MyTripViewHolder>(MyTripAdapter.MyTripDiffCallback()) {

    inner class MyTripViewHolder(
        val binding: ItemMyTripBinding,
        val rowClickListener: MyTripClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(eventAttraction: EventsAndAttraction, isLast: Boolean) {

            binding.data = eventAttraction

                if (currentLanguage != Locale.ENGLISH) {
//                    val dayCount = Integer.parseInt(day.duration.substring(0, day.duration.indexOf(" ")))
                    binding.tvCar.text = eventAttraction.duration
                        .replace("1","١")
                        .replace("2","٢")
                        .replace("3","٣")
                        .replace("4","٤")
                        .replace("5","٥")
                        .replace("6","٦")
                        .replace("7","٧")
                        .replace("8","٨")
                        .replace("9","٩")
                        .replace("0","٠")
                    binding.tvDistance.text = eventAttraction.distance
                        .replace("1","١")
                        .replace("2","٢")
                        .replace("3","٣")
                        .replace("4","٤")
                        .replace("5","٥")
                        .replace("6","٦")
                        .replace("7","٧")
                        .replace("8","٨")
                        .replace("9","٩")
                        .replace("0","٠")
                    binding.tvTime.text = eventAttraction.displayTimeFrom
                        .replace("1","١")
                        .replace("2","٢")
                        .replace("3","٣")
                        .replace("4","٤")
                        .replace("5","٥")
                        .replace("6","٦")
                        .replace("7","٧")
                        .replace("8","٨")
                        .replace("9","٩")
                        .replace("0","٠") + " - " +
                            eventAttraction.displayTimeTo
                                .replace("1","١")
                                .replace("2","٢")
                                .replace("3","٣")
                                .replace("4","٤")
                                .replace("5","٥")
                                .replace("6","٦")
                                .replace("7","٧")
                                .replace("8","٨")
                                .replace("9","٩")
                                .replace("0","٠") + " | " +
                            eventAttraction.day
                } else {
                    binding.tvCar.text = eventAttraction.duration
                        .replace("١","1")
                        .replace("٢","2",)
                        .replace("٣","3")
                        .replace("٤","4")
                        .replace("٥","5")
                        .replace("٦","6")
                        .replace("٧","7")
                        .replace("٨","8")
                        .replace("٩","9")
                        .replace("٠","0")
                    binding.tvDistance.text = eventAttraction.distance
                        .replace("١","1")
                        .replace("٢","2",)
                        .replace("٣","3")
                        .replace("٤","4")
                        .replace("٥","5")
                        .replace("٦","6")
                        .replace("٧","7")
                        .replace("٨","8")
                        .replace("٩","9")
                        .replace("٠","0")
                    binding.tvTime.text = eventAttraction.displayTimeFrom
                        .replace("١","1")
                        .replace("٢","2",)
                        .replace("٣","3")
                        .replace("٤","4")
                        .replace("٥","5")
                        .replace("٦","6")
                        .replace("٧","7")
                        .replace("٨","8")
                        .replace("٩","9")
                        .replace("٠","0") + " - " +
                            eventAttraction.displayTimeTo
                                .replace("١","1")
                                .replace("٢","2",)
                                .replace("٣","3")
                                .replace("٤","4")
                                .replace("٥","5")
                                .replace("٦","6")
                                .replace("٧","7")
                                .replace("٨","8")
                                .replace("٩","9")
                                .replace("٠","0") + " | " +
                            eventAttraction.day
                }

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
                binding.llIcons.hide()
                binding.tvCar.hide()
//                binding.cvNavigation.hide()
                binding.tvDistance.hide()
            }else{
                binding.llIcons.show()
                binding.tvCar.show()
//                binding.cvNavigation.show()
                binding.tvDistance.show()
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