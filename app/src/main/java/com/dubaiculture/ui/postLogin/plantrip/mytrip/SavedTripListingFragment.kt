package com.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.Trip
import com.dubaiculture.databinding.FragmentSavedTripListingBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.SaveTripAdapter
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.SaveTripClickListener
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.SaveTripListingViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
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
                tripSharedViewModel.setDatesFromAPI(it.dateTimeFilter)

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