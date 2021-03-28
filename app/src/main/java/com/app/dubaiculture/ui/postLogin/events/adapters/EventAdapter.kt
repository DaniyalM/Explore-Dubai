package com.app.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.EventItemsBinding
import com.app.dubaiculture.databinding.ItemEventListingBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.AsyncCell
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.item_event_listing.view.*

class EventAdapter (private val favChecker : FavouriteChecker?=null, private val rowClickListener: RowClickListener?=null) : BaseRecyclerAdapter<Events>() {


    var events: List<Events>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class EventsListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventsListViewHolder(EventsListItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpEventsListViewHolder(holder as EventAdapter.EventsListViewHolder,
            position)
    }

    override fun getItemCount() = events.size

    //Data Binding
    private inner class EventsListItemCell(context: Context) : AsyncCell(context,true) {
        var binding: EventItemsBinding? = null
        override val layoutId = R.layout.event_items
        override fun createDataBindingView(view: View): View? {
            binding = EventItemsBinding.bind(view)
            YoYo.with(Techniques.BounceInDown)
                .duration(700)
                .playOn(binding?.root)
            return view.rootView
        }
    }

    private fun setUpEventsListViewHolder(
        holder: EventAdapter.EventsListViewHolder,
        position: Int,
    ) {
        (holder.itemView as EventsListItemCell).bindWhenInflated {
            holder.itemView.binding?.let {
                try {

                    it.events = events[position]

                    it.favourite.setOnClickListener {
                        events[position].let {event->
                            event.id?.let { itemId->
                                favChecker!!.checkFavListener(it.favourite,position,event.isFavourite,itemId)
                            }
                        }
                    }
                    it.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }

//                    it.favourite.setOnCheckedChangeListener { p0, p1 ->
//                        if(events[position].isFavourite){
//
//                        }else{
//
//                        }
//                        if (p1){
//                            it.favourite.isChecked = p1
//                            it.favourite.isSelected = p1
//                        }
//                        else {
//                            it.favourite.isChecked = p1
//                            it.favourite.isSelected = p1
//                            Toast.makeText(context,
//                           "check",
//                            Toast.LENGTH_SHORT).show()
//                        }
////
//
//                    }

                } catch (ex: IndexOutOfBoundsException) {
                    print(ex.stackTrace)
                }
            }
        }
    }
}