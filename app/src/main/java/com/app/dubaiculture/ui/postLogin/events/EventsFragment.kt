package com.app.dubaiculture.ui.postLogin.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.EventHomeListing
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.components.recylerview.clicklisteners.RecyclerItemClickListener
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListScreenAdapter
import com.app.dubaiculture.ui.postLogin.events.adapters.EventRecyclerAsyncAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.bumptech.glide.RequestManager
import com.google.android.material.shape.CornerFamily
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.attraction_detail_inner_layout.view.*
import kotlinx.android.synthetic.main.attraction_detail_inner_layout.view.cardview_plan_trip
import kotlinx.android.synthetic.main.plan_a_trip_layout.view.*
import javax.inject.Inject

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>() {
    private lateinit var event: EventRecyclerAsyncAdapter
    private lateinit  var eventAdapter: EventListScreenAdapter
    private lateinit  var moreAdapter: EventListScreenAdapter


    private val eventViewModel: EventViewModel by viewModels()


    @Inject
    lateinit var glide: RequestManager
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventsBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvSetUp()
        cardViewRTL()
//        binding.swipeRefresh.setOnRefreshListener {
//            binding.swipeRefresh.isRefreshing = false
//        }
        binding.tvViewMap.setOnClickListener {
            navigate(R.id.action_eventsFragment_to_eventNearMapFragment2)
        }

    }

    private fun rvSetUp() {
        eventAdapter = EventListScreenAdapter()
        moreAdapter = EventListScreenAdapter()
        binding.rvEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = eventAdapter
            this.itemAnimator = SlideInLeftAnimator()
            this.addOnItemTouchListener(RecyclerItemClickListener(
                activity,
                this,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
//                        attractionViewModel.showErrorDialog(message = attractions.get(position).title)
                        navigate(R.id.action_eventsFragment_to_eventFilterFragment)
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                    }
                }
            ))
        }
        eventAdapter.events = createAttractionItems()
        binding.rvMoreEvent.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = moreAdapter
            this.itemAnimator = SlideInLeftAnimator()
        }
        moreAdapter.events = createAttractionItems()

    }

    private fun cardViewRTL(){
        val radius = resources.getDimension(R.dimen.my_corner_radius_plan)
        if(isArabic()){
            binding.root.cardivewRTL?.shapeAppearanceModel =  binding.root.cardivewRTL!!.shapeAppearanceModel
                .toBuilder()
                .setBottomLeftCorner(CornerFamily.ROUNDED,radius)
                .setTopRightCornerSize(radius)
                .build()
        }else{
            binding.root.cardivewRTL?.shapeAppearanceModel =  binding.root.cardivewRTL!!.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED,radius)
                .setBottomRightCornerSize(radius)
                .build()
        }
    }

    private fun createTestItems(): List<EventHomeListing> =
        mutableListOf<EventHomeListing>().apply {


            repeat((1..2).count()) {
                when (it % 2) {
                    0 -> {
                        add(
                            EventHomeListing(
                                title = "FeatureEvents",
                                category = "FeatureEvents",
                                events = createAttractionItems()
                            )
                        )
                    }
                    else -> {
                        add(
                            EventHomeListing(
                                title = "More Events",
                                category = "MoreEvents",
                                events = createAttractionItems()
                            )
                        )
                    }
                }


            }
        }

    private fun createAttractionItems(): ArrayList<Events> = mutableListOf<Events>().apply {
        repeat((1..4).count()) {
            add(
                Events(
                    id = it.toString(),
                    title = "Title $it",
                    category = "Category $it",
                    fromDate = "18",
                    fromMonthYear = "Mar, 21",
                    fromTime = "20$it",
                    fromDay = "1$it",
                    toDate = "20",
                    toMonthYear = "Mar, 21",
                    toTime = "202$it",
                    toDay = "2$it",
                    type = "Free"
                )
            )
        }
    } as ArrayList<Events>
}