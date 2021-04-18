package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.local.models.LatestNews
import com.app.dubaiculture.data.repository.explore.local.models.PopularServices
import com.app.dubaiculture.databinding.*
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionCategoryListItem
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionListItem
import com.app.dubaiculture.ui.postLogin.attractions.mappers.transformBaseToAttraction
import com.app.dubaiculture.ui.postLogin.attractions.mappers.transformBaseToAttractionCategory
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.ui.postLogin.events.mapper.transformBaseToEvent
import com.app.dubaiculture.ui.postLogin.explore.ExploreFragment
import com.app.dubaiculture.ui.postLogin.explore.adapters.itemcells.*
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.LatestNewsListItem
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceListItem
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_CAT_OBJECT
import com.google.android.material.shape.CornerFamily
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


class ExploreRecyclerAsyncAdapter internal constructor(
    private val context: Context,
    private var isArabic: Boolean? = null,
    private var fragment: ExploreFragment? = null,
    private var baseViewModel: BaseViewModel?=null
) :
    BaseRecyclerAdapter<Explore>() {
    private var attractionInnerAdapter: GroupAdapter<GroupieViewHolder>? = null
    private var upComingEventsInnerAdapter: GroupAdapter<GroupieViewHolder>? = null
    private var mustSeeInnerAdapter: GroupAdapter<GroupieViewHolder>? = null
    private var latestNewsInnerAdapter: GroupAdapter<GroupieViewHolder>? = null
    private var popularServiceInnerAdapter: GroupAdapter<GroupieViewHolder>? = null

    //global variable
    companion object {
        val handler = Handler(Looper.getMainLooper())
        var delayAnimate = 300
    }

    init {
        attractionInnerAdapter = GroupAdapter()
        upComingEventsInnerAdapter = GroupAdapter()
        mustSeeInnerAdapter = GroupAdapter()
        latestNewsInnerAdapter = GroupAdapter()
        popularServiceInnerAdapter = GroupAdapter()

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

        (holder.itemView as AttractionItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.let { it.innerSectionHeading.text = item.title }

                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = attractionInnerAdapter
                    attractionInnerAdapter?.let {
                        if (it.itemCount>0){
                            attractionInnerAdapter?.clear()
                        }
                    }


                    item.value.forEach { attractionCat ->
                        attractionInnerAdapter?.add(AttractionCategoryListItem<AttractionsCategoryItemCellBinding>(
                            attractionCat = transformBaseToAttractionCategory(attractionCat),
                            rowClickListener = object:RowClickListener{
                                override fun rowClickListener(position: Int) {
                                   fragment?.navigate(R.id.action_exploreFragment_to_attractionsFragment,Bundle().apply {
                                       putInt(ATTRACTION_CAT_OBJECT,position)
                                   })
                                }
                            },
                            isArabic = isArabic ?: false)
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
        (holder.itemView as UpComingEventsItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = upComingEventsInnerAdapter
                    upComingEventsInnerAdapter?.let {
                        if (it.itemCount>0){
                            upComingEventsInnerAdapter?.clear()
                        }
                    }
                    item.value.forEach {
                        upComingEventsInnerAdapter?.add(EventListItem<UpcomingEventsInnerItemCellBinding>(
                            favChecker = object : FavouriteChecker {
                                override fun checkFavListener(
                                    checkbox: CheckBox,
                                    pos: Int,
                                    isFav: Boolean,
                                    itemId: String,
                                ) {
                                    fragment?.favouriteClick(
                                        checkbox,
                                        isFav,
                                        R.id.action_exploreFragment_to_postLoginFragment,
                                        itemId, baseViewModel!!,
                                        1
                                    )
                                }

                            },
                            rowClickListener = object : RowClickListener {
                                override fun rowClickListener(position: Int) {
                                    fragment?.navigate(R.id.action_exploreFragment_to_eventDetailFragment2,
                                        Bundle().apply {
                                            putParcelable(Constants.NavBundles.EVENT_OBJECT,
                                                transformBaseToEvent(it))
                                        })
                                }
                            },
                            resLayout = R.layout.upcoming_events_inner_item_cell,
                            event = transformBaseToEvent(it),
                            context = context))
                    }

                }

                holder.itemView.binding?.let {
                    it.innerSectionHeading.text = item.title
                    it.viewAll.visibility = View.VISIBLE
                    it.viewAll.setOnClickListener {
                        fragment?.navigate(R.id.action_exploreFragment_to_eventFilterFragment)
                    }
                }

            }
        }
    }

    private fun setMustSeeViewHolder(
        holder: ExploreRecyclerAsyncAdapter.MustSeeViewHolder,
        position: Int,
    ) {
        (holder.itemView as MustSeeItemCell).bindWhenInflated {
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
                holder.itemView.binding?.let { it.innerSectionHeading.text = item.title }
                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = mustSeeInnerAdapter

                    mustSeeInnerAdapter?.let {
                        if (it.itemCount>0){
                            mustSeeInnerAdapter?.clear()
                        }
                    }
                    item.value.forEach { attraction ->
                        mustSeeInnerAdapter?.add(AttractionListItem<MustSeeInnerItemCellBinding>(
                            favChecker = object : FavouriteChecker {
                                override fun checkFavListener(
                                    checkbox: CheckBox,
                                    pos: Int,
                                    isFav: Boolean,
                                    itemId: String,
                                ) {
                                    fragment?.favouriteClick(
                                        checkbox,
                                        isFav,
                                        R.id.action_exploreFragment_to_postLoginFragment,
                                        itemId, baseViewModel!!,
                                    )
                                }

                            },
                            rowClickListener = object : RowClickListener {
                                override fun rowClickListener(position: Int) {
                                    fragment?.navigate(R.id.action_exploreFragment_to_attractionDetailFragment,
                                        Bundle().apply {
                                            putParcelable(Constants.NavBundles.ATTRACTION_OBJECT,
                                                transformBaseToAttraction(attraction))
                                        })

                                }
                            },
                            resLayout = R.layout.must_see_inner_item_cell,
                            attraction = transformBaseToAttraction(attraction),
                            context = context
                        ))
                    }

                }

            }
        }
    }

    private fun setLatestNewsViewHolder(
        holder: ExploreRecyclerAsyncAdapter.LatestNewViewHolder,
        position: Int,
    ) {
        (holder.itemView as LatestNewsItemCell).bindWhenInflated {
            items[position].let { item ->

                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = latestNewsInnerAdapter
                    latestNewsInnerAdapter?.let {
                        if (it.itemCount>0){
                            latestNewsInnerAdapter?.clear()
                        }
                    }

                    item.value.forEach {
                        latestNewsInnerAdapter?.add(LatestNewsListItem<LatestNewsInnerItemCellBinding>(
                            news = LatestNews(
                                id = it.id,
                                title = it.title,
                                image = it.image,
                                postedDate = it.postedDate,
                                date = it.date
                            )
                        ))
                    }

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
        (holder.itemView as PopularServiceCell).bindWhenInflated {
            items[position].let { item ->

                holder.itemView.binding?.innerSectionRv?.let {
                    it.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    it.adapter = popularServiceInnerAdapter
                    popularServiceInnerAdapter?.let {
                        if (it.itemCount>0){
                            popularServiceInnerAdapter?.clear()
                        }
                    }
                    item.value.forEach {
                        popularServiceInnerAdapter?.add(PopularServiceListItem<PopularServiceInnerItemCellBinding>(
                            resLayout = R.layout.popular_service_inner_item_cell,
                            services = PopularServices(
                                id = it.id,
                                title = it.title,
                                coloredIcon = it.coloredIcon,
                                jsonFile = it.jsonFile,
                                hoveredJsonFile = it.hoveredJsonFile,
                                playJson = it.playJson,
                                icon = it.icon
                            )
                        ))
                    }

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
