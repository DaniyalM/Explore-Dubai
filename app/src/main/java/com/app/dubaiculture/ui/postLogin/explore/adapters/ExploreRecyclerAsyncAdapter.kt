package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Attraction
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.local.models.MustSee
import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents
import com.app.dubaiculture.databinding.SectionItemContainerCellBinding
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionInnerAdapter
import com.app.dubaiculture.ui.postLogin.events.adapters.UpComingEventsInnerAdapter
import com.app.dubaiculture.ui.postLogin.explore.mustsee.adapters.MustSeeInnerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager

class ExploreRecyclerAsyncAdapter internal constructor(
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var glide: RequestManager
    var items: List<Explore>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int = items.size

    fun provideGlideInstance(glide: RequestManager) {
        this.glide = glide
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
        when (viewType) {
            ViewTypes.ATTRACTION.type -> AttractionViewHolder(AttractionItemCell(parent.context).apply { inflate() })
            ViewTypes.MUSTSEE.type -> MustSeeViewHolder(MustSeeItemCell(parent.context).apply { inflate() })
            else -> UpComingEventsViewHolder(UpComingEventsItemCell(parent.context).apply { inflate() })
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AttractionViewHolder -> setUpAttractionViewHolder(holder, position)
            is UpComingEventsViewHolder -> setUpComingEventsViewHolder(holder, position)
            is MustSeeViewHolder -> setMustSeeViewHolder(holder, position)
        }
    }

    //Setting Up View Holders
    private fun setUpAttractionViewHolder(
        holder: ExploreRecyclerAsyncAdapter.AttractionViewHolder,
        position: Int
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val attractionInnerAdapter = AttractionInnerAdapter(glide)
        (holder.itemView as ExploreRecyclerAsyncAdapter.AttractionItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager = layoutManager
                    it.adapter = attractionInnerAdapter
                    attractionInnerAdapter.attractions = item.value.map {
                        Attraction(
                            id = it.id,
                            title = it.title,
                            image_url = it.image_url
                        )
                    }
                }
            }
        }
    }

    private fun setUpComingEventsViewHolder(
        holder: ExploreRecyclerAsyncAdapter.UpComingEventsViewHolder,
        position: Int
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val upComingEventsInnerAdapter = UpComingEventsInnerAdapter(glide)
        (holder.itemView as ExploreRecyclerAsyncAdapter.UpComingEventsItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager = layoutManager
                    it.adapter = upComingEventsInnerAdapter
                    upComingEventsInnerAdapter.upComingEvents = item.value.map {
                        UpComingEvents(
                            id = it.id,
                            title = it.title,
                            category = it.category,
                            location = it.location,
                            start_date = it.starting_date,
                            end_date = it.ending_data,
                            price_tag = it.price_tag,
                            image_url = it.image_url,
                            is_liked = it.is_liked,
                            date = it.date
                        )
                    }
                }
            }
        }
    }

    private fun setMustSeeViewHolder(
        holder: ExploreRecyclerAsyncAdapter.MustSeeViewHolder,
        position: Int
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val mustSeeInnerAdapter = MustSeeInnerAdapter(glide)
        (holder.itemView as ExploreRecyclerAsyncAdapter.MustSeeItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager = layoutManager
                    it.adapter = mustSeeInnerAdapter
                    mustSeeInnerAdapter.mustSees = item.value.map {
                        MustSee(
                            id = it.id,
                            title = it.title,
                            category = it.category,
                            image_url = it.image_url,
                            is_liked = it.is_liked
                        )
                    }
                }
            }
        }
    }

    //Setting Up View Holders


    //View Holders of View Type

    inner class AttractionViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    inner class MustSeeViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    inner class UpComingEventsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    //View Holders of View Type

    //        items[position].code % 2 == 0 -> ViewTypes.ATTRACTION.type
    override fun getItemViewType(position: Int): Int =
        when (items[position].category) {
            "attraction" -> ViewTypes.ATTRACTION.type
            "must_see" -> ViewTypes.MUSTSEE.type
            "upcoming_events" -> ViewTypes.UPCOMINGEVENTS.type
            else -> ViewTypes.ATTRACTION.type
        }


    //Item Cells of ViewTypes
    private inner class AttractionItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            binding?.let { it.innerSectionHeading.text = "Attractions" }
            return view.rootView
        }
    }

    private inner class MustSeeItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            binding?.let { it.innerSectionHeading.text = "Must See" }
            return view.rootView
        }
    }

    private inner class UpComingEventsItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            binding?.let { it.innerSectionHeading.text = "Upcoming Events" }
            return view.rootView
        }
    }
    //Item Cells of ViewTypes


//    private inner class LargeItemCell(context: Context) : AsyncCell(context) {
//        var binding: LargeItemCellBinding? = null
//        override val layoutId = R.layout.large_item_cell
//        override fun createDataBindingView(view: View): View? {
//            binding = LargeItemCellBinding.bind(view)
//            return view.rootView
//        }
//    }


    //Item Cells of ViewTypes

    enum class ViewTypes(val type: Int) {

        ATTRACTION(0),
        MUSTSEE(1),
        PLANYOURTRIP(3),
        UPCOMINGEVENTS(2),
        POPULARSERVICES(4),
        LATESTNEWS(5),
    }

}