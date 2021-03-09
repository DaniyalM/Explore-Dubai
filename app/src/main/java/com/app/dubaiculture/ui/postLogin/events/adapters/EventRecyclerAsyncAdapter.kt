package com.app.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.databinding.SectionItemContainerCellBinding
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager

class EventRecyclerAsyncAdapter internal constructor(
    private val context: Context, private var isArabic: Boolean? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var featureEventsInnerAdapter: EventListScreenAdapter? = null
    private var moreEventsInnerAdapter: EventListScreenAdapter? = null

    init {
        featureEventsInnerAdapter = EventListScreenAdapter()
        moreEventsInnerAdapter = EventListScreenAdapter()
    }

    var items: List<EventHomeListing>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int = items.size


    private val diffCallback = object : DiffUtil.ItemCallback<EventHomeListing>() {
        override fun areItemsTheSame(
            oldItem: EventHomeListing,
            newItem: EventHomeListing,
        ): Boolean {
            return oldItem.events == newItem.events
        }

        override fun areContentsTheSame(
            oldItem: EventHomeListing,
            newItem: EventHomeListing,
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewTypes.FEATUREEVENTS.type -> {
                FeatureEventsViewHolder(FeatureEventsItemCell(parent.context).apply { inflate() })
            }
            else -> MoreEventsViewHolder(MoreEventsItemCell(parent.context).apply { inflate() })

        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventRecyclerAsyncAdapter.FeatureEventsViewHolder -> setUpFeatureEventsViewHolder(
                holder,
                position)
            is EventRecyclerAsyncAdapter.MoreEventsViewHolder -> setUpMoreEventsViewHolder(holder,
                position)
        }
    }


    inner class FeatureEventsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    inner class MoreEventsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int =
        when (items[position].category) {
            "FeatureEvents" -> ViewTypes.FEATUREEVENTS.type
            else -> ViewTypes.MOREEVENTS.type
        }

    //Item Cells of ViewTypes
    private inner class FeatureEventsItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            return view.rootView
        }
    }

    private inner class MoreEventsItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            return view.rootView
        }
    }

    enum class ViewTypes(val type: Int) {
        FEATUREEVENTS(0),
        MOREEVENTS(1),
    }

    private fun setUpFeatureEventsViewHolder(
        holder: EventRecyclerAsyncAdapter.FeatureEventsViewHolder,
        position: Int,
    ) {
        (holder.itemView as EventRecyclerAsyncAdapter.FeatureEventsItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = featureEventsInnerAdapter
                    featureEventsInnerAdapter?.events = item.events
                }
                holder.itemView.binding?.let { it.innerSectionHeading.text =  item.title}
            }



        }
    }

    private fun setUpMoreEventsViewHolder(
        holder: EventRecyclerAsyncAdapter.MoreEventsViewHolder,
        position: Int,
    ) {
        (holder.itemView as EventRecyclerAsyncAdapter.MoreEventsItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = moreEventsInnerAdapter
                    moreEventsInnerAdapter?.events = item.events
                    holder.itemView.binding?.let { it.innerSectionHeading.text =  item.title}

                }
            }
        }

    }
}