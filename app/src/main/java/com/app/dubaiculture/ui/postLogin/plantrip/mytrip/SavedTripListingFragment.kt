package com.app.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.app.dubaiculture.data.repository.trip.local.Trip
import com.app.dubaiculture.databinding.FragmentSavedTripListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionInnerAdapter
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionListingViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.MyTripAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.SaveTripAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.MyTripClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.SaveTripClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.SaveTripListingViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.SaveTripViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SavedTripListingFragment : BaseFragment<FragmentSavedTripListingBinding>() {

    private val saveTripListingViewModel: SaveTripListingViewModel by viewModels()
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private lateinit var saveTripAdapter: SaveTripAdapter

    @Inject
    lateinit var glide: RequestManager
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSavedTripListingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        setupRV()

    }

    private fun setupRV() {

        binding.rvTrips.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            saveTripAdapter = SaveTripAdapter(
                glide,
                object : SaveTripClickListener {
                    override fun rowClickListener(trip: Trip) {
                        saveTripListingViewModel.getTripDetails(
                            trip.tripId,
                            application.auth.locale.toString()
                        )
                    }

                    override fun rowClickListener(
                        trip: Trip,
                        position: Int
                    ) {
                    }

                }
            )
            adapter = saveTripAdapter


        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
        subscribeUiEvents(saveTripListingViewModel)
    }

    private fun subscribeToObservables() {

        saveTripListingViewModel.tripPagination.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                saveTripAdapter.submitData(it)
            }
        }
        saveTripListingViewModel.eventAttraction.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                tripSharedViewModel._showSave.value = false
                tripSharedViewModel._eventAttractionResponse.value = it
                tripSharedViewModel.setDatesFromAPI(it.eventsAndAttractions)

                navigate(R.id.action_tripSavingListingFragment_to_my_tripFragment)

            }
        }
        tripSharedViewModel.trip.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                saveTripListingViewModel.filterTrips(it)
            }
        }

    }

}