package com.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.databinding.EventNearItemsBinding
import com.dubaiculture.ui.postLogin.events.`interface`.DirectionClickListener
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.AsyncCell
import com.dubaiculture.utils.dateFormat

class EventNearMapAdapter(var isArabic : Boolean,var rowClickListener: RowClickListener, var directionClickListener: DirectionClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Events>() {
        override fun areItemsTheSame(oldItem: Events, newItem: Events): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Events, newItem: Events): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var events: List<Events>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class EventNearMapViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventNearMapViewHolder(EventsListItemCell(parent.context).apply { inflate() })

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpEventNearMapViewHolder(holder = holder as EventNearMapAdapter.EventNearMapViewHolder,
            position)
    }
    override fun getItemCount() = events.size
    //Data Binding
    private inner class EventsListItemCell(context: Context) : AsyncCell(context,true) {
        var binding: EventNearItemsBinding? = null
        override val layoutId = R.layout.event_near_items
        override fun createDataBindingView(view: View): View? {
            binding = EventNearItemsBinding.bind(view)
            return view.rootView
        }
    }
    private fun setUpEventNearMapViewHolder(
        holder: EventNearMapAdapter.EventNearMapViewHolder,
        position: Int,
    ) {
        (holder.itemView as EventNearMapAdapter.EventsListItemCell).bindWhenInflated {
            holder.itemView.binding?.let {
                arrowtRTL(isArabic,it.arrow)
                try {
                    it.img.setOnClickListener {
                        directionClickListener.directionClickListener(position)
                    }
                    it.cl.setOnClickListener {
                        rowClickListener.rowClickListener(position)
                    }
                    it.events = events[position]
                    it.tvDate.text = dateFormat(events[position].dateTo)
                } catch (ex: IndexOutOfBoundsException) {
                    print(ex.stackTrace)
                }
            }
        }
    }

    fun arrowtRTL(isArabic : Boolean,img: ImageView) {
        when {
            isArabic -> {
                img.scaleX = -1f
            }
            else -> {
                img.scaleX = 1f
            }
        }
    }
}