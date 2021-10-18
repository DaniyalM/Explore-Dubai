package com.app.dubaiculture.ui.postLogin.popular_service.myservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.ServiceBookings
import com.app.dubaiculture.data.repository.explore.local.models.ServiceStatus
import com.app.dubaiculture.databinding.FragmentMyServicesBinding
import com.app.dubaiculture.databinding.ItemServiceCompletedPendingLayoutBinding
import com.app.dubaiculture.databinding.ItemsBookATicketLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceListItem
import com.app.dubaiculture.ui.postLogin.popular_service.models.ServiceHeader
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class MyServicesFragment : BaseFragment<FragmentMyServicesBinding>() {
    private lateinit var linearLayoutManger: LinearLayoutManager
    private var groupAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var serviceStatusAdapter: GroupAdapter<GroupieViewHolder>? = GroupAdapter()


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentMyServicesBinding.inflate(inflater, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customTextView3.text = activity.resources.getString(R.string.my_services)
        binding.headerVisited.back.setOnClickListener {
            back()
        }
         binding.horizontalSelector.initialize(initializeHeaders())
        initServiceRvListing()
        subscribeToObservables()
    }

    private fun initServiceRvListing() {
        linearLayoutManger = LinearLayoutManager(activity)
        binding.rvServiceStatusListing.apply {
            layoutManager = linearLayoutManger
            adapter = groupAdapter
        }
        linearLayoutManger = LinearLayoutManager(activity)
        binding.rvAttractionListing.apply {
            layoutManager = linearLayoutManger
            adapter = serviceStatusAdapter
        }
    }

    private fun subscribeToObservables() {
        binding.horizontalSelector.headerPosition.observe(viewLifecycleOwner) {
            when (it) {
                0 -> {
                    addMyServices()
                }
                else -> {
                    addServiceStatus()
                }
            }
        }
    }

    private fun addMyServices() {
        binding.rvAttractionListing.visibility = View.GONE
        binding.rvServiceStatusListing.visibility=View.VISIBLE
        groupAdapter.apply {
            if (this.itemCount > 0) {
                this.clear()
            }
            testBookingTickets().forEach {
                add(PopularServiceListItem<ItemsBookATicketLayoutBinding>(
                        resLayout = R.layout.items_book_a_ticket_layout,
                        servicesBookings = it
                ))
            }
        }
    }
    private fun addServiceStatus(){
        binding.rvServiceStatusListing.visibility=View.GONE
        binding.rvAttractionListing.visibility = View.VISIBLE
        serviceStatusAdapter.apply {
            if(this!!.itemCount >0){
                this.clear()
            }
            testServiceStatus().forEachIndexed {index,it->
                if(index == 1){
                    it.completed = false
                }
                add(PopularServiceListItem<ItemServiceCompletedPendingLayoutBinding>(
                        resLayout = R.layout.item_service_completed_pending_layout,
                        myServiceStatus = it,
                ))
            }
        }
    }


    private fun testBookingTickets(): MutableList<ServiceBookings> {
        val placesVisited = ArrayList<ServiceBookings>()
        repeat(10) {
            placesVisited.add(ServiceBookings())
        }
        return placesVisited
    }

    private fun testServiceStatus(): MutableList<ServiceStatus> {
        val placesVisited = ArrayList<ServiceStatus>()
        repeat(3) {
            placesVisited.add(ServiceStatus())
        }
        return placesVisited
    }
    private fun initializeHeaders(): MutableList<ServiceHeader> {
        val placesVisited = ArrayList<ServiceHeader>()
        repeat(2) {
            val serviceHeader = ServiceHeader(selectedIcon = null, unselectedIcon = null)

            when (it) {
                0 -> {
                    serviceHeader.apply {
                        id = it
                        title = getString(R.string.booking_tickets)
                        selectedColor = R.color.purple_900
                        unSelectedColor = R.color.white_900
                        selectedIcon = R.drawable.bookingticketspurpleicon
                        unselectedIcon = R.drawable.bookingticketsicon
                    }
                }
                1 -> {
                    serviceHeader.apply {
                        id = it
                        title = getString(R.string.service_status)
                        selectedColor = R.color.purple_900
                        unSelectedColor = R.color.white_900
                        selectedIcon = R.drawable.servicesiconwhite
                        unselectedIcon = R.drawable.servicesicon
                    }
                    addServiceStatus()
                }
            }
            placesVisited.add(serviceHeader)

        }


        return placesVisited
    }
}