package com.app.dubaiculture.ui.postLogin.events

import android.content.Context
import android.os.Bundle
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
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionBusService
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListScreenAdapter
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventListingFragment : BaseFragment<FragmentEventListingBinding>() {
    private val eventViewModel: EventViewModel by viewModels()
    private var eventListScreenAdapter: EventListScreenAdapter? = null
    private lateinit var eventList: ArrayList<Events>

    companion object {
        var EVENT_CATEG0RY_TYPE: String = "Events"
        var EVENT_DETAIL_ID: String = "Event_ID"

        @JvmStatic
        fun newInstance(eventList: ArrayList<Events>) = EventListingFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(EVENT_CATEG0RY_TYPE, eventList)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(eventViewModel)
        initRecyclerView()
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            bus.post(AttractionBusService().SwipeToRefresh(true))
        }

    }

    private fun initRecyclerView() {
        eventListScreenAdapter = EventListScreenAdapter()
        binding.rvEventListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = eventListScreenAdapter
            eventListScreenAdapter?.events = eventList
            this.addOnItemTouchListener(RecyclerItemClickListener(
                activity,
                this,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
//                        attractionViewModel.showErrorDialog(message = attractions.get(position).title)
                        navigateByAction(R.id.action_loginFragment_to_eventDetailFragment,
                            Bundle().apply {
                                this.putString(EVENT_DETAIL_ID,
                                    eventList[position].id)
                            })
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                    }
                }
            ))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getParcelableArrayList<Events>(EVENT_CATEG0RY_TYPE)
            ?.let { eventList = it }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventListingBinding.inflate(inflater, container, false)

}