package com.app.dubaiculture.ui.postLogin.events

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListScreenAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class EventListingFragment : BaseFragment<FragmentEventListingBinding>() {
    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var eventListScreenAdapter: EventListScreenAdapter
    var eventID: String? = ""

    companion object {
        var EVENT_CATEG0RY_TYPE: String = "Events"
        var EVENT_DETAIL_ID: String = "Event_ID"

        @JvmStatic
        fun newInstance(eventID: String? = "") = EventListingFragment().apply {
            arguments = Bundle().apply {
                putString(EVENT_DETAIL_ID, eventID!!)
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(eventViewModel)
        initRecyclerView()
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            // bus.post(AttractionBusService().SwipeToRefresh(true))
        }

    }

    private fun initRecyclerView() {
        eventListScreenAdapter = EventListScreenAdapter(object : FavouriteChecker {
            override fun checkFavListener(checkbox: CheckBox, pos: Int, isFav: Boolean) {
                favouriteEvent(application.auth.isGuest,
                    checkbox,
                    isFav,
                    R.id.action_eventFilterFragment_to_postLoginFragment)
            }
        }, object : RowClickListener {
            override fun rowClickListener() {
                navigate(R.id.action_eventFilterFragment_to_eventDetailFragment2)
            }
        })
        binding.rvEventListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = eventListScreenAdapter
            eventListScreenAdapter.events = createAttractionItems()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(EVENT_DETAIL_ID)
            ?.let {
                eventID = it
                Timber.e("Get Position =>${eventID.toString()}")
            }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventListingBinding.inflate(inflater, container, false)


    private fun createAttractionItems(): ArrayList<Events> = mutableListOf<Events>().apply {
        repeat((1..4).count()) {
            add(
                Events(
                    id = it.toString(),
                    title = "The Definitive Guide to an Uncertain World",
                    category = "ONLINE",
                    fromDate = "18",
                    fromMonthYear = "Mar, 21",
                    fromTime = "20$it",
                    fromDay = "1$it",
                    toDate = "20",
                    toMonthYear = "Mar, 21",
                    toTime = "202$it",
                    toDay = "2$it",
                    type = "FREE",
                    locationTitle = "Palm Jumeriah, Dubai"
                )
            )
        }
    } as ArrayList<Events>
}