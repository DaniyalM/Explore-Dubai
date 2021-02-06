package com.app.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Attraction
import com.app.dubaiculture.data.repository.explore.local.models.InnerValue
import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents
import com.app.dubaiculture.databinding.UpcomingEventsInnerItemCellBinding
import com.app.dubaiculture.databinding.UpcomingEventsInnerItemCellBindingImpl
import com.app.dubaiculture.ui.postLogin.explore.adapters.ExploreRecyclerAsyncAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.attractions_item_cell.view.*

class UpComingEventsInnerAdapter(val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<InnerValue>() {
        override fun areItemsTheSame(oldItem:InnerValue, newItem: InnerValue): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: InnerValue, newItem: InnerValue): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var upComingEvents: List<InnerValue>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    inner class UpComingEventsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UpComingEventsViewHolder(UpcomingEventsInnerItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {is UpComingEventsInnerAdapter.UpComingEventsViewHolder ->  setUpcomingEventsViewHolder(holder,position) }
    }

    override fun getItemCount() = upComingEvents.size

    private inner class UpcomingEventsInnerItemCell(context: Context) : AsyncCell(context,630) {
        var binding: UpcomingEventsInnerItemCellBinding? = null
        override val layoutId = R.layout.upcoming_events_inner_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = UpcomingEventsInnerItemCellBinding.bind(view)
            return view.rootView
        }
    }
    private fun setUpcomingEventsViewHolder(
        holder: UpComingEventsInnerAdapter.UpComingEventsViewHolder,
        position: Int
    ) {
        (holder.itemView as UpComingEventsInnerAdapter.UpcomingEventsInnerItemCell).bindWhenInflated {
            upComingEvents[position].let { item ->
//                holder.itemView.binding?.innerSectionRv?.let {
//
//                }
            }
        }
    }


}