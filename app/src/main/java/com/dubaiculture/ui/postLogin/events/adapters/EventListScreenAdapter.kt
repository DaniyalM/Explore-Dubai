package com.dubaiculture.ui.postLogin.events.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.databinding.EventItemsBinding
import com.dubaiculture.databinding.ItemEventListingBinding
import com.dubaiculture.ui.postLogin.events.`interface`.EventClickListner
import com.dubaiculture.utils.Constants.EventOrientation.HorizontalLength
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class EventListScreenAdapter(
        private val eventClickListner: EventClickListner,
        private val orientationFlag: Int = HorizontalLength
) : ListAdapter<Events, EventListScreenAdapter.EventsListViewHolder>(
        EventComparator()
) {


    class EventComparator : DiffUtil.ItemCallback<Events>() {
        override fun areItemsTheSame(oldItem: Events, newItem: Events) =
                oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: Events, newItem: Events) = oldItem.id == newItem.id
    }


    inner class EventsListViewHolder(
            val binding: ItemEventListingBinding,
            private val eventClickListner: EventClickListner,
    ) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Events) {
            binding.apply {
                YoYo.with(Techniques.BounceInDown)
                    .duration(2000)
                    .playOn(root)
                events = event
                event.let { event ->
                    favourite.setOnClickListener {
                        event.id?.let { itemId ->
                            eventClickListner.checkFavListener(
                                binding.favourite,
                                absoluteAdapterPosition,
                                event.isFavourite,
                                itemId
                            )

                        }

                    }
                    binding.cardview.setOnClickListener {
                        eventClickListner.rowClickHandler(event)
                    }

                }


            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsListViewHolder =
            EventsListViewHolder(
                    ItemEventListingBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                    ),
                eventClickListner
            )


    override fun onBindViewHolder(holder: EventsListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


}