package com.app.dubaiculture.ui.postLogin.popular_service.myservices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.ServiceBookings
import com.app.dubaiculture.databinding.FragmentMyServicesBinding
import com.app.dubaiculture.databinding.ItemMyServiceLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceListItem
import com.app.dubaiculture.ui.postLogin.popular_service.models.ServiceHeader
import com.app.dubaiculture.ui.postLogin.popular_service.service.PopularServiceBus
import com.squareup.otto.Subscribe

class MyServicesFragment : BaseFragment<FragmentMyServicesBinding>() {
    private lateinit var linearLayoutManger: LinearLayoutManager
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentMyServicesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.customTextView3.text = activity.resources.getString(R.string.my_services)
        binding.headerVisited.back.setOnClickListener {
            back()
        }
        binding.horizontalSelector.initialize(initializeHeaders(), bus)
        initServiceRvListing()

    }

    private fun initServiceRvListing() {
        linearLayoutManger = LinearLayoutManager(activity)
        binding.rvServiceListing.apply {
            layoutManager = linearLayoutManger
            adapter = groupAdapter
        }
    }



    @Subscribe
    fun handleHeaderClick(popularServiceBus: PopularServiceBus) {
        when (popularServiceBus) {
            is PopularServiceBus.HeaderItemClick -> {
                when (popularServiceBus.position) {
                    0 -> {
                        addMyServices()
                    }
                    else -> {
                       binding.rvServiceListing.visibility=View.GONE
                    }
                }
            }
        }
    }



    private fun addMyServices() {
        binding.rvServiceListing.visibility=View.VISIBLE
        groupAdapter.apply {
            if (this.itemCount > 0) {
                this.clear()
            }

            testPlaces().forEach {
                add(PopularServiceListItem<ItemMyServiceLayoutBinding>(
                        resLayout = R.layout.item_my_service_layout,
                        servicesBookings = it
                ))
            }
        }
    }


    private fun testPlaces(): MutableList<ServiceBookings> {
        val placesVisited = ArrayList<ServiceBookings>()
        repeat(10) {
            placesVisited.add(ServiceBookings())
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
                        title = "Booking & Tickets"
                        selectedColor = R.color.purple_900
                        unSelectedColor = R.color.white_900
                        selectedIcon = R.drawable.bookingticketspurpleicon
                        unselectedIcon = R.drawable.bookingticketsicon
                    }
                }
                1 -> {
                    serviceHeader.apply {
                        id = it
                        title = "Service Status"
                        selectedColor = R.color.purple_900
                        unSelectedColor = R.color.white_900
                        selectedIcon = R.drawable.servicesiconwhite
                        unselectedIcon = R.drawable.servicesicon
                    }
                }
            }
            placesVisited.add(serviceHeader)

        }


        return placesVisited
    }
}