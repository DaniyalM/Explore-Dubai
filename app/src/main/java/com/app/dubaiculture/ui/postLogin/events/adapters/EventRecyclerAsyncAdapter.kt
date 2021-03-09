package com.app.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.databinding.SectionItemContainerCellBinding
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionInnerAdapter
import com.app.dubaiculture.ui.postLogin.explore.adapters.ExploreRecyclerAsyncAdapter
import com.app.dubaiculture.ui.postLogin.explore.mustsee.adapters.MustSeeInnerAdapter
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.LatestNewsInnerAdapter
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceInnerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager
import com.squareup.javawriter.JavaWriter.type

class EventRecyclerAsyncAdapter internal constructor(
    private val context: Context, private var isArabic : Boolean?=null
) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var featureEventsInnerAdapter: UpComingEventsInnerAdapter? = null
    private var moreEventsInnerAdapter: UpComingEventsInnerAdapter? = null

    var items: List<Explore>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int = items.size

    fun provideGlideInstance(glide: RequestManager) {
        featureEventsInnerAdapter = UpComingEventsInnerAdapter(glide)
        moreEventsInnerAdapter = UpComingEventsInnerAdapter(glide)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Explore>() {
        override fun areItemsTheSame(oldItem: Explore, newItem: Explore): Boolean {
            return oldItem.value == newItem.value
        }
        override fun areContentsTheSame(oldItem: Explore, newItem: Explore): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when(viewType){
            ViewTypes.FEATUREEVENTS.type->{FeatureEventsViewHolder(FeatureEventsItemCell(parent.context).apply { inflate() })}
            else  -> MoreEventsViewHolder(MoreEventsItemCell(parent.context).apply { inflate() })

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventRecyclerAsyncAdapter.FeatureEventsViewHolder -> setUpFeatureEventsViewHolder(holder, position)
            is EventRecyclerAsyncAdapter.MoreEventsViewHolder -> setUpMoreEventsViewHolder(holder, position)
        }
    }





    inner class  FeatureEventsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    inner class  MoreEventsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

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
        (holder.itemView as EventRecyclerAsyncAdapter.FeatureEventsItemCell).bindWhenInflated {}
        }

    private fun setUpMoreEventsViewHolder(
        holder: EventRecyclerAsyncAdapter.MoreEventsViewHolder,
        position: Int,
    ) {
        (holder.itemView as EventRecyclerAsyncAdapter.MoreEventsItemCell).bindWhenInflated {}

    }
}