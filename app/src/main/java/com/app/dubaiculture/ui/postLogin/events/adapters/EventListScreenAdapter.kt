package com.app.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.ItemEventListingBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.utils.AsyncCell

class EventListScreenAdapter : BaseRecyclerAdapter<Events>() {


    var events: List<Events>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class EventsListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventsListViewHolder(EventsListItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpEventsListViewHolder(holder as EventListScreenAdapter.EventsListViewHolder,
            position)
    }

    override fun getItemCount() = events.size

    //Data Binding
    private inner class EventsListItemCell(context: Context) : AsyncCell(context) {
        var binding: ItemEventListingBinding? = null
        override val layoutId = R.layout.item_event_listing
        override fun createDataBindingView(view: View): View? {
            binding = ItemEventListingBinding.bind(view)
            return view.rootView
        }
    }

    private fun setUpEventsListViewHolder(
        holder: EventListScreenAdapter.EventsListViewHolder,
        position: Int,
    ) {
        (holder.itemView as EventsListItemCell).bindWhenInflated {
            holder.itemView.binding?.let {
                try {
                    it.events = events[position]
                    it.favourite.setOnCheckedChangeListener(null)

                    it.favourite.setOnCheckedChangeListener { p0, p1 ->
                        if (p1){
                            it.favourite.isChecked = p1
                            it.favourite.isSelected = p1
                        }
                        else {
                            it.favourite.isChecked = p1
                            it.favourite.isSelected = p1
                        }
//                        Toast.makeText(context,
//                            events[position].title,
//                            Toast.LENGTH_SHORT).show()

                    }

                } catch (ex: IndexOutOfBoundsException) {
                    print(ex.stackTrace)
                }
            }
        }
    }
}