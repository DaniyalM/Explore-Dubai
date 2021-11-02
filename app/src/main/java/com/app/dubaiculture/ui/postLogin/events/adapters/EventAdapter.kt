package com.app.dubaiculture.ui.postLogin.events.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.EventItemsBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.EventClickListner
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class EventAdapter(private val favChecker: FavouriteChecker? = null,
                   private val rowClickListener: RowClickListener? = null,
                   private val eventClickListner: EventClickListner? = null)


    : ListAdapter<Events, EventAdapter.EventsListViewHolder>(EventListScreenAdapter.EventComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsListViewHolder {
        return EventsListViewHolder(
                EventItemsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                ),
                eventClickListner
        )
    }

    override fun onBindViewHolder(holder: EventsListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class EventsListViewHolder(val binding: EventItemsBinding, val eventClickListner: EventClickListner?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(events: Events) {
            YoYo.with(Techniques.BounceInDown)
                    .duration(700)
                    .playOn(binding.root)
            binding.apply {

                this.events = events

                favourite.setOnClickListener {
                    events.let { event ->
                        event.id?.let { itemId ->
                            eventClickListner!!.checkFavListener(favourite, absoluteAdapterPosition, event.isFavourite, itemId)
//                            favChecker?.checkFavListener(binding.favourite, position, event.isFavourite, itemId)
                        }
                    }
                }
                cardview.setOnClickListener {
//                    rowClickListener?.rowClickListener(position)
                    eventClickListner!!.rowClickHandler(events)
                }
            }
        }
    }


}