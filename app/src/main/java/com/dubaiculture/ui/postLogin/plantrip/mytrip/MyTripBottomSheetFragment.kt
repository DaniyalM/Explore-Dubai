package com.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.Duration
import com.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.dubaiculture.databinding.FragmentMyTripBottomsheetBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.DatesAdapter
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.MyTripAdapter
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.DateClickListener
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.MyTripClickListener
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyTripBottomSheetFragment : BaseBottomSheetFragment<FragmentMyTripBottomsheetBinding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()

    private lateinit var datesAdapter: DatesAdapter
    private lateinit var myTripAdapter: MyTripAdapter


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMyTripBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        isCancelable = false
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
                        tripSharedViewModel.updateDate(duration.copy(isSelected = true))
                    }

                    override fun rowClickListener(duration: Duration, position: Int) {
                    }

                }
            )
            adapter = datesAdapter


        }

        binding.rvTrips.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            myTripAdapter = MyTripAdapter(
                object : MyTripClickListener {
                    override fun rowClickListener(eventAttraction: EventsAndAttraction) {
                    }

                    override fun rowClickListener(
                        eventAttraction: EventsAndAttraction,
                        position: Int
                    ) {
                    }

                    override fun rowClickListenerDirections(latitude: String, longitude: String) {
                        TODO("Not yet implemented")
                    }

                },
                getCurrentLanguage()
            )
            adapter = myTripAdapter


        }

    }

    private fun subscribeToObservables() {



//        tripSharedViewModel.eventAttractionList.observe(viewLifecycleOwner) {
//            if(it != null)
//            myTripAdapter.submitList(it)
//        }

        tripSharedViewModel.dates.observe(viewLifecycleOwner) {
            binding.tvDate.text = it.single { it.isSelected }.dayDate.substring(3)
//            tripSharedViewModel.filterEvents(it.single { it.isSelected })
            datesAdapter.submitList(it)

        }

    }

    fun onBottomSheetClicked() {

//        navigate(R.id.action_my_trip_to_my_trip_listing)

    }

    fun onSaveTripClicked() {
        navigate(R.id.action_myTrip_bottom_sheet_to_myTripNameDialog)
    }

    fun onTravelModeClicked() {
        navigate(R.id.action_my_trip_to_travel_mode_dialog)

    }

}