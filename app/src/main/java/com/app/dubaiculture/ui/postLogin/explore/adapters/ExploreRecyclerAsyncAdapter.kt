package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.databinding.SectionItemContainerCellBinding
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionInnerAdapter
import com.app.dubaiculture.ui.postLogin.events.adapters.UpComingEventsInnerAdapter
import com.app.dubaiculture.ui.postLogin.explore.mustsee.adapters.MustSeeInnerAdapter
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.LatestNewsInnerAdapter
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceInnerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager

class ExploreRecyclerAsyncAdapter internal constructor(
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var attractionInnerAdapter: AttractionInnerAdapter? = null
    private var upComingEventsInnerAdapter: UpComingEventsInnerAdapter? = null
    private var mustSeeInnerAdapter: MustSeeInnerAdapter? = null
    private var latestNewsInnerAdapter: LatestNewsInnerAdapter? = null
    private var popularServiceInnerAdapter: PopularServiceInnerAdapter? = null



    var items: List<Explore>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int = items.size

    fun provideGlideInstance(glide: RequestManager) {


        attractionInnerAdapter = AttractionInnerAdapter(glide)
        upComingEventsInnerAdapter = UpComingEventsInnerAdapter(glide)
        mustSeeInnerAdapter = MustSeeInnerAdapter(glide)
        latestNewsInnerAdapter = LatestNewsInnerAdapter(glide)
        popularServiceInnerAdapter = PopularServiceInnerAdapter(glide)


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
            ViewTypes.UPCOMINGEVENTS.type -> UpComingEventsViewHolder(UpComingEventsItemCell(parent.context).apply { inflate() })
            ViewTypes.POPULARSERVICES.type -> PopularServiceViewHolder(PopularServiceCell(parent.context).apply { inflate() })
//            ViewTypes.LATESTNEWS.type ->
            else -> LatestNewViewHolder(LatestNewsItemCell(parent.context).apply { inflate() })

        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is AttractionViewHolder -> setUpAttractionViewHolder(holder, position)
            is UpComingEventsViewHolder -> setUpComingEventsViewHolder(holder, position)
            is MustSeeViewHolder -> setMustSeeViewHolder(holder, position)
            is PopularServiceViewHolder -> setPopularServiceViewHolder(holder, position)
            is LatestNewViewHolder -> setLatestNewsViewHolder(holder, position)
        }
    }

    //Setting Up View Holders
    private fun setUpAttractionViewHolder(
        holder: ExploreRecyclerAsyncAdapter.AttractionViewHolder,
        position: Int
    ) {

        (holder.itemView as ExploreRecyclerAsyncAdapter.AttractionItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = attractionInnerAdapter
                    attractionInnerAdapter?.attractions = item.value
                }
            }
        }
    }

    private fun setUpComingEventsViewHolder(
        holder: ExploreRecyclerAsyncAdapter.UpComingEventsViewHolder,
        position: Int
    ) {
        (holder.itemView as ExploreRecyclerAsyncAdapter.UpComingEventsItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = upComingEventsInnerAdapter
                    upComingEventsInnerAdapter?.upComingEvents = item.value
                }
            }
        }
    }

    private fun setMustSeeViewHolder(
        holder: ExploreRecyclerAsyncAdapter.MustSeeViewHolder,
        position: Int
    ) {
        (holder.itemView as ExploreRecyclerAsyncAdapter.MustSeeItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.cardviewPlanTrip?.visibility = View.VISIBLE
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = mustSeeInnerAdapter
                    mustSeeInnerAdapter?.mustSees = item.value
                }
            }
        }
    }

    private fun setLatestNewsViewHolder(
        holder: ExploreRecyclerAsyncAdapter.LatestNewViewHolder,
        position: Int
    ) {
        (holder.itemView as ExploreRecyclerAsyncAdapter.LatestNewsItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = latestNewsInnerAdapter
                    latestNewsInnerAdapter?.latestNews = item.value
                }
            }
        }
    }

    private fun setPopularServiceViewHolder(
        holder: ExploreRecyclerAsyncAdapter.PopularServiceViewHolder,
        position: Int
    ) {
        (holder.itemView as ExploreRecyclerAsyncAdapter.PopularServiceCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = popularServiceInnerAdapter
                    popularServiceInnerAdapter?.popularService = item.value
                }
            }
        }
    }


    //Setting Up View Holders


    //View Holders of View Type

    inner class AttractionViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    inner class MustSeeViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    inner class UpComingEventsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    inner class PopularServiceViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    inner class LatestNewViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)


    //View Holders of View Type

    //        items[position].code % 2 == 0 -> ViewTypes.ATTRACTION.type
    override fun getItemViewType(position: Int): Int =
        when (items[position].category) {
            "attraction" -> ViewTypes.ATTRACTION.type
            "must_see" -> ViewTypes.MUSTSEE.type
            "upcoming_events" -> ViewTypes.UPCOMINGEVENTS.type
            "popular_services" -> ViewTypes.POPULARSERVICES.type
            "latest_news" -> ViewTypes.LATESTNEWS.type
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
    private inner class PopularServiceCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            binding?.let { it.innerSectionHeading.text = "Popular Service" }
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
    private inner class LatestNewsItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            binding?.let { it.innerSectionHeading.text = "Latest News" }
            return view.rootView
        }
    }



    //Item Cells of ViewTypes
    enum class ViewTypes(val type: Int) {
        ATTRACTION(0),
        MUSTSEE(1),
        UPCOMINGEVENTS(2),
        POPULARSERVICES(3),
        LATESTNEWS(4),
    }
}