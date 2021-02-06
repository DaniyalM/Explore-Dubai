package com.app.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.UpcomingEventsInnerItemCellBinding
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager

class UpComingEventsInnerAdapter(val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<BaseModel>() {
        override fun areItemsTheSame(oldItem:BaseModel, newItem: BaseModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var upComingEvents: List<BaseModel>
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

    private inner class UpcomingEventsInnerItemCell(context: Context) : AsyncCell(context,true) {
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