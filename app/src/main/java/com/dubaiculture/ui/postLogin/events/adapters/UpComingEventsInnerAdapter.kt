package com.dubaiculture.ui.postLogin.events.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.dubaiculture.ui.postLogin.explore.adapters.itemcells.UpcomingEventsInnerItemCell

class UpComingEventsInnerAdapter :
    BaseRecyclerAdapter<Events>() {


    var upComingEvents: List<Events>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    inner class UpComingEventsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UpComingEventsViewHolder(UpcomingEventsInnerItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UpComingEventsInnerAdapter.UpComingEventsViewHolder -> setUpcomingEventsViewHolder(
                holder,
                position)
        }
    }

    override fun getItemCount() = upComingEvents.size


    private fun setUpcomingEventsViewHolder(
        holder: UpComingEventsInnerAdapter.UpComingEventsViewHolder,
        position: Int,
    ) {
        (holder.itemView as UpcomingEventsInnerItemCell).bindWhenInflated {
            holder.itemView.binding?.events = upComingEvents[position]
        }
    }
}