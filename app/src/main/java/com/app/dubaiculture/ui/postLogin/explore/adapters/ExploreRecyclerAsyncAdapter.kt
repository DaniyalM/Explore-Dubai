package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.attraction.local.models.Gallery
import com.app.dubaiculture.data.repository.attraction.local.models.SocialLink
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.databinding.SectionItemContainerCellBinding
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionInnerAdapter
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.UpComingEventsInnerAdapter
import com.app.dubaiculture.ui.postLogin.explore.ExploreFragment
import com.app.dubaiculture.ui.postLogin.explore.mustsee.adapters.MustSeeInnerAdapter
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.LatestNewsInnerAdapter
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceInnerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.google.android.material.shape.CornerFamily


class ExploreRecyclerAsyncAdapter internal constructor(
    private val context: Context,
    private var isArabic: Boolean? = null,
    private var fragment: ExploreFragment ?= null,
    private var application: ApplicationEntry? = null
) :
    BaseRecyclerAdapter<Explore>() {
    private var attractionInnerAdapter: AttractionInnerAdapter? = null


    private var upComingEventsInnerAdapter: UpComingEventsInnerAdapter? = null
    private var mustSeeInnerAdapter: MustSeeInnerAdapter? = null
    private var latestNewsInnerAdapter: LatestNewsInnerAdapter? = null
    private var popularServiceInnerAdapter: PopularServiceInnerAdapter? = null

    //global variable
    companion object {
        val handler = Handler(Looper.getMainLooper())
        var delayAnimate = 300
    }

    init {
        attractionInnerAdapter = AttractionInnerAdapter(
            object : RowClickListener {
                override fun rowClickListener() {
//                    navigate(R.id.action_eventFilterFragment_to_eventDetailFragment2)
                }

            },
            isArabic ?: false)
        upComingEventsInnerAdapter = UpComingEventsInnerAdapter()
        mustSeeInnerAdapter = MustSeeInnerAdapter(object : FavouriteChecker {
                override fun checkFavListener(
                    checkbox: CheckBox,
                    pos: Int,
                    isFav: Boolean,
                ) {
                    fragment?.favouriteEvent(application!!.auth.isGuest,
                        checkbox,
                        isFav,
                        R.id.action_exploreFragment_to_postLoginFragment)
                }
            },fragment)
        latestNewsInnerAdapter = LatestNewsInnerAdapter()
        popularServiceInnerAdapter = PopularServiceInnerAdapter()
    }

    var items: List<Explore>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun getItemCount(): Int = items.size

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
        setAnimation(holder.itemView)
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
        position: Int,
    ) {

        (holder.itemView as ExploreRecyclerAsyncAdapter.AttractionItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.let { it.innerSectionHeading.text = item.title }

                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = attractionInnerAdapter
                    attractionInnerAdapter?.attractions = item.value.map { attractionCat ->
                        AttractionCategory(
                            id = attractionCat.id,
                            icon = attractionCat.icon,
                            title = attractionCat.title,
                            selectedSvg = attractionCat.selectedSvg,
                            color = attractionCat.color
                        )
                    }
                }

            }
        }
    }

    private fun setUpComingEventsViewHolder(
        holder: ExploreRecyclerAsyncAdapter.UpComingEventsViewHolder,
        position: Int,
    ) {
        (holder.itemView as ExploreRecyclerAsyncAdapter.UpComingEventsItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = upComingEventsInnerAdapter
                    upComingEventsInnerAdapter?.upComingEvents = item.value
//                    it.addOnItemTouchListener(
//                        RecyclerItemClickListener(
//                            context,
//                            it,
//                            object : RecyclerItemClickListener.OnItemClickListener {
//                                override fun onItemClick(view: View, position: Int) {
//                                    Toast.makeText(
//                                        context,
//                                        "${item.value.get(position).title} : ${
//                                            item.value.get(position).category
//                                        }",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//
//                                override fun onLongItemClick(view: View, position: Int) {
//
//                                }
//                            })
//                    )
                }
                holder.itemView.binding?.let {
                    it.innerSectionHeading.text = item.title
                    it.viewAll.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun setMustSeeViewHolder(
        holder: ExploreRecyclerAsyncAdapter.MustSeeViewHolder,
        position: Int,
    ) {
        (holder.itemView as ExploreRecyclerAsyncAdapter.MustSeeItemCell).bindWhenInflated {
            items[position].let { item ->
                val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
                if (isArabic == true) {
                    holder.itemView.binding?.cardviewPlanTrip?.shapeAppearanceModel =
                        holder.itemView.binding?.cardviewPlanTrip!!.shapeAppearanceModel
                            .toBuilder()
                            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
                            .setTopRightCornerSize(radius)
                            .build()
                } else {
                    holder.itemView.binding?.cardviewPlanTrip?.shapeAppearanceModel =
                        holder.itemView.binding?.cardviewPlanTrip!!.shapeAppearanceModel
                            .toBuilder()
                            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                            .setBottomRightCornerSize(radius)
                            .build()
                }

                holder.itemView.binding?.cardviewPlanTrip?.visibility = View.VISIBLE
                holder.itemView.binding?.tripSeperator?.visibility = View.VISIBLE
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = mustSeeInnerAdapter
                    mustSeeInnerAdapter?.mustSees = item.value.map { attraction ->
                        Attractions(
                            id = attraction.id.toString(),
                            title = attraction.title.toString(),
                            category = attraction.category.toString(),
                            locationTitle = attraction.locationTitle,
                            location = attraction.location,
                            portraitImage = attraction.portraitImage,
                            landscapeImage = attraction.landscapeImage,
                            description = attraction.description,
                            startTime = attraction.startTime,
                            endTime = attraction.endTime,
                            startDay = attraction.startDay,
                            endDay = attraction.endDay,
                            color = attraction.color,
                            IsFavourite = attraction.isFavourite,
                            events = attraction.events?.let {
                                it.map {
                                    Events(
                                        id = it.id,
                                        title = it.title,
                                        category = it.category,
                                        image = it.image,
                                        fromDate = it.fromDate,
                                        fromMonthYear = it.fromMonthYear,
                                        fromTime = it.fromTime,
                                        fromDay = it.fromDay,
                                        toDate = it.toDate,
                                        toMonthYear = it.toMonthYear,
                                        toTime = it.toTime,
                                        toDay = it.toDay,
                                        type = it.type,
//            color=it.color,
                                        dateTo = it.dateTo,
                                        dateFrom = it.dateFrom,
                                        locationTitle = it.locationTitle,
                                        location = it.location,
                                        longitude = it.longitude,
                                        latitude = it.latitude,
                                        isFavourite = it.isFavourite,
                                    )
                                }
                            },
                            gallery = attraction.gallery?.let {
                                it.map {
                                    Gallery(
                                        isImage = it.isImage,
                                        galleryImage = it.galleryImage,
                                        galleryThumbnail = it.galleryThumbnail,
                                        galleryLink = it.galleryLink
                                    )
                                }
                            },
                            socialLink = attraction.socialLinks?.let {
                                it.map {
                                    SocialLink(
                                        facebookPageLink = it.facebookPageLink.toString(),
                                        facebookIcon = it.facebookIcon.toString(),
                                        instagramIcon = it.instagramIcon,
                                        instagramPageLink = it.instagramPageLink.toString()
                                    )
                                }
                            }
                        )

                    }
                }
                holder.itemView.binding?.let { it.innerSectionHeading.text = item.title }

            }
        }
    }

    private fun setLatestNewsViewHolder(
        holder: ExploreRecyclerAsyncAdapter.LatestNewViewHolder,
        position: Int,
    ) {
        (holder.itemView as ExploreRecyclerAsyncAdapter.LatestNewsItemCell).bindWhenInflated {
            items[position].let { item ->

                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = latestNewsInnerAdapter
                    latestNewsInnerAdapter?.latestNews = item.value
//                    it.addOnItemTouchListener(
//                        RecyclerItemClickListener(
//                            context,
//                            it,
//                            object : RecyclerItemClickListener.OnItemClickListener {
//                                override fun onItemClick(view: View, position: Int) {
//                                    Toast.makeText(
//                                        context,
//                                        "${item.value.get(position).title} : ${
//                                            item.value.get(position).category
//                                        }",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//
//                                override fun onLongItemClick(view: View, position: Int) {
//
//                                }
//                            })
//                    )
                }
                holder.itemView.binding?.let {
                    it.innerSectionHeading.text = item.title
                    it.viewAll.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun setPopularServiceViewHolder(
        holder: ExploreRecyclerAsyncAdapter.PopularServiceViewHolder,
        position: Int,
    ) {
        (holder.itemView as ExploreRecyclerAsyncAdapter.PopularServiceCell).bindWhenInflated {
            items[position].let { item ->

                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = popularServiceInnerAdapter
                    popularServiceInnerAdapter?.popularService = item.value
//                    it.addOnItemTouchListener(
//                        RecyclerItemClickListener(
//                            context,
//                            it,
//                            object : RecyclerItemClickListener.OnItemClickListener {
//                                override fun onItemClick(view: View, position: Int) {
//                                    Toast.makeText(
//                                        context,
//                                        "${item.value.get(position).title} : ${
//                                            item.value.get(position).category
//                                        }",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//
//                                override fun onLongItemClick(view: View, position: Int) {
//
//                                }
//                            })
//                    )
                }
                holder.itemView.binding?.let {
                    it.innerSectionHeading.text = item.title
                    it.viewAll.visibility = View.VISIBLE
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
            "AttractionsCategory" -> ViewTypes.ATTRACTION.type
            "Attractions" -> ViewTypes.MUSTSEE.type
            "Events" -> ViewTypes.UPCOMINGEVENTS.type
            "EServices" -> ViewTypes.POPULARSERVICES.type
            "News" -> ViewTypes.LATESTNEWS.type
            else -> ViewTypes.ATTRACTION.type
        }


    //Item Cells of ViewTypes
    private inner class AttractionItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)

            return view.rootView
        }
    }

    private inner class MustSeeItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            return view.rootView
        }
    }

    private inner class PopularServiceCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            return view.rootView
        }
    }

    private inner class UpComingEventsItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
            return view.rootView
        }
    }

    private inner class LatestNewsItemCell(context: Context) : AsyncCell(context) {
        var binding: SectionItemContainerCellBinding? = null
        override val layoutId = R.layout.section_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = SectionItemContainerCellBinding.bind(view)
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

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        stopAnimation()
    }


    private fun setAnimation(view: View?) {
        handler.postDelayed({
            val animation: Animation = AnimationUtils.loadAnimation(
                context,
                android.R.anim.slide_in_left
            )
            if (view != null) {
                view.startAnimation(animation)
                view.visibility = View.VISIBLE
            }
        }, delayAnimate.toLong())
        delayAnimate += 500
    }

    private fun stopAnimation() {
        handler.removeCallbacksAndMessages(null)
    }
}

//
