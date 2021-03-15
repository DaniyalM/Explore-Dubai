package com.app.dubaiculture.ui.postLogin.events

import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.components.recylerview.clicklisteners.RecyclerItemClickListener
import com.app.dubaiculture.ui.postLogin.attractions.AttractionListingFragment
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionBusService
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListScreenAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventListingFragment : BaseFragment<FragmentEventListingBinding>() {
    private val eventViewModel: EventViewModel by viewModels()
    private lateinit var eventListScreenAdapter: EventListScreenAdapter
    private lateinit var eventID: String


    companion object {
        var EVENT_CATEG0RY_TYPE: String = "Events"
        var EVENT_DETAIL_ID: String = "Event_ID"

        @JvmStatic
        fun newInstance(eventID: String?="") = EventListingFragment().apply {
            arguments = Bundle().apply {
                putString(EVENT_DETAIL_ID, eventID)
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
        eventListScreenAdapter = EventListScreenAdapter()
        binding.rvEventListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = eventListScreenAdapter
            eventListScreenAdapter?.events = createAttractionItems()
            this.addOnItemTouchListener(RecyclerItemClickListener(
                activity,
                this,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
//                        attractionViewModel.showErrorDialog(message = attractions.get(position).title)
                    }
                    override fun onLongItemClick(view: View, position: Int) {
                    }
                }
            ))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(EVENT_CATEG0RY_TYPE)
            ?.let { eventID = it }
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