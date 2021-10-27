package com.app.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.Duration
import com.app.dubaiculture.databinding.FragmentMyTripBottomsheetBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.DatesAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.DateClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners.DurationClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyTripBottomSheetFragment : BaseBottomSheetFragment<FragmentMyTripBottomsheetBinding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()

    private lateinit var datesAdapter: DatesAdapter


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMyTripBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        setupRV()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }

    private fun setupRV() {

        binding.rvDates.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            datesAdapter = DatesAdapter(
                object : DateClickListener {
                    override fun rowClickListener(duration: Duration) {

//                        tripSharedViewModel.updateDurationList(duration)

                    }

                    override fun rowClickListener(duration: Duration, position: Int) {
                    }

                }
            )
            adapter = datesAdapter


        }

    }
    
    private fun subscribeToObservables() {

        tripSharedViewModel.eventAttractionResponse.observe(viewLifecycleOwner){
            showToast(it.location.locationTitle)
        }

        tripSharedViewModel.dates.observe(viewLifecycleOwner){

            datesAdapter.submitList(it)

        }

    }

    fun onBottomSheetClicked() {

        navigate(R.id.action_my_trip_to_my_trip_listing)

    }

    fun onSaveTripClicked(){
        navigate(R.id.action_myTrip_bottom_sheet_to_myTripNameDialog)
    }

    fun onTravelModeClicked(){
        navigate(R.id.action_my_trip_to_travel_mode_dialog)

    }

}