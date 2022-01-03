package com.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.Trip
import com.dubaiculture.databinding.FragmentSavedTripListingBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.components.loadstateadapter.DefaultLoadStateAdapter
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.SaveTripAdapter
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.SaveTripClickListener
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.SaveTripListingViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show
import com.dubaiculture.utils.withLoadStateAdapters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SavedTripListingFragment : BaseFragment<FragmentSavedTripListingBinding>() {

    private val saveTripListingViewModel: SaveTripListingViewModel by viewModels()
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private lateinit var saveTripAdapter: SaveTripAdapter

    private lateinit var observer: RecyclerView.AdapterDataObserver

    @Inject
    lateinit var glide: RequestManager
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSavedTripListingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        backArrowRTL(binding.imgClose)
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
                .withLoadStateAdapters(
                    DefaultLoadStateAdapter(),
                    DefaultLoadStateAdapter(), callback = {
                        if (it) {
                            binding.tvPlaceHolder.show()
                            binding.rvTrips.hide()
                        } else {
                            binding.tvPlaceHolder.hide()
                            binding.rvTrips.show()

                        }

                    }
                )

            observer = object :
                RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    checkEmpty()
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    checkEmpty()
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    super.onItemRangeRemoved(positionStart, itemCount)
                    checkEmpty()
                }

                fun checkEmpty() {
                    binding.tvPlaceHolder.visibility =
                        (if (saveTripAdapter.itemCount == 0) View.VISIBLE else View.GONE)
                    binding.rvTrips.visibility =
                        (if (saveTripAdapter.itemCount > 0) View.VISIBLE else View.GONE)
                }
            }
            saveTripAdapter.registerAdapterDataObserver(observer)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveTripAdapter.unregisterAdapterDataObserver(observer)
//        searchFilterSelectedAdapter.unregisterAdapterDataObserver(observerSelected)

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