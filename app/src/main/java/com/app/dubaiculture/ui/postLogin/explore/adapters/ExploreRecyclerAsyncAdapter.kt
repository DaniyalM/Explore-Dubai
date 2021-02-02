package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.*
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
            ViewTypes.UPCOMINGEVENTS.type -> UpComingEventsViewHolder(UpComingEventsItemCell(parent.context).apply { inflate() })
            ViewTypes.POPULARSERVICES.type -> PopularServiceViewHolder(PopularServiceCell(parent.context).apply { inflate() })
            ViewTypes.LATESTNEWS.type-> LatestNewViewHolder(LatestNewsItemCell(parent.context).apply { inflate() })
            else ->PopularServiceViewHolder(PopularServiceCell(parent.context).apply { inflate() })

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
                holder.itemView.binding?.cardviewPlanTrip?.visibility = View.VISIBLE
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


    private fun setLatestNewsViewHolder(holder: ExploreRecyclerAsyncAdapter.LatestNewViewHolder, position: Int) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val latestNewsInnerAdapter = LatestNewsInnerAdapter(glide)
        (holder.itemView as ExploreRecyclerAsyncAdapter.LatestNewsItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager = layoutManager
                    it.adapter = latestNewsInnerAdapter
                    latestNewsInnerAdapter.latestNews = item.value.map {
                        LatestNews(
                            id = it.id,
                            title = it.title,
                            category = it.category,
                            image_url = it.image_url,
                            date = it.date
                        )
                    }
                }
            }
        }
    }

    private fun setPopularServiceViewHolder(holder: ExploreRecyclerAsyncAdapter.PopularServiceViewHolder, position: Int) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val popularServiceInnerAdapter = PopularServiceInnerAdapter(glide)
        (holder.itemView as ExploreRecyclerAsyncAdapter.PopularServiceCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager = layoutManager
                    it.adapter = popularServiceInnerAdapter
                    popularServiceInnerAdapter.popularService = item.value.map {
                        PopularServices(
                            id = it.id,
                            title = it.title,
                            image_url = it.image_url
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
        PLANYOURTRIP(4),
        UPCOMINGEVENTS(2),
        POPULARSERVICES(3),
        LATESTNEWS(5),
    }
}