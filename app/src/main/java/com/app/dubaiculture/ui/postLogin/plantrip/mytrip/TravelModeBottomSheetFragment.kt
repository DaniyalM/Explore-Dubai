package com.app.dubaiculture.ui.postLogin.plantrip.mytrip

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.app.dubaiculture.databinding.FragmentTravelModeBottomSheetBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TravelModeViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TravelModeBottomSheetFragment :
    BaseBottomSheetFragment<FragmentTravelModeBottomSheetBinding>() {

    private lateinit var tripList: List<EventsAndAttraction>
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private val travelModeViewModel: TravelModeViewModel by viewModels()
    private lateinit var travelMode: String
    private lateinit var currentLocation: Location


    @Inject
    lateinit var locationHelper: LocationHelper
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            //  locationIsEmpty(locationResult.lastLocation)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTravelModeBottomSheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        binding.viewModel = tripSharedViewModel

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        locationPermission()
        subscribeUiEvents(travelModeViewModel)
    }

    private fun locationPermission() {
        val quickPermissionsOption = QuickPermissionsOptions(
            handleRationale = false
        )
        activity.runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            options = quickPermissionsOption
        ) {
            locationHelper.locationSetUp(
                object : LocationHelper.LocationLatLng {
                    @SuppressLint("SetTextI18n")
                    override fun getCurrentLocation(location: Location) {

                        currentLocation = location
                        subscribeToObservables()


                    }
                },
                locationCallback
            )
        }
    }

    private fun subscribeToObservables() {

        tripSharedViewModel.travelMode.observe(viewLifecycleOwner) {
            when (it) {
                Constants.TRAVEL_MODE.DRIVING -> {
                    onDrivingClicked()
                }
                Constants.TRAVEL_MODE.WALKING -> {
                    onWalkingClicked()
                }
                Constants.TRAVEL_MODE.TRANSIT -> {
                    onTrainClicked()
                }
                Constants.TRAVEL_MODE.BICYCLING -> {
                    onBusClicked()
                }

            }
        }

        travelModeViewModel.distanceResponse.observe(viewLifecycleOwner) {
            it.rows[0].elements.map {
                if (it.status == "ZERO_RESULTS") {
                    showAlert(Constants.TRAVEL_MODE.ERROR)
                    return@observe
                }
            }
            tripSharedViewModel._travelMode.value = travelMode
            tripSharedViewModel.mapDistanceInList(it,travelMode)
            dismiss()

        }

        tripSharedViewModel.eventAttractionList.observe(viewLifecycleOwner) {
            if (it != null) {
                tripList = it
            }
        }


    }

    fun onDrivingClicked() {
        binding.radioButton.isChecked = true
        binding.radioButtonWalking.isChecked = false
        binding.radioButtonTrain.isChecked = false
        binding.radioButtonBus.isChecked = false
        travelMode = Constants.TRAVEL_MODE.DRIVING
    }

    fun onWalkingClicked() {

        binding.radioButton.isChecked = false
        binding.radioButtonWalking.isChecked = true
        binding.radioButtonTrain.isChecked = false
        binding.radioButtonBus.isChecked = false
        travelMode = Constants.TRAVEL_MODE.WALKING

    }

    fun onTrainClicked() {

        binding.radioButton.isChecked = false
        binding.radioButtonWalking.isChecked = false
        binding.radioButtonTrain.isChecked = true
        binding.radioButtonBus.isChecked = false
        travelMode = Constants.TRAVEL_MODE.TRANSIT

    }

    fun onBusClicked() {

        binding.radioButton.isChecked = false
        binding.radioButtonWalking.isChecked = false
        binding.radioButtonTrain.isChecked = false
        binding.radioButtonBus.isChecked = true
        travelMode = Constants.TRAVEL_MODE.BICYCLING

    }

    fun onDoneClicked() {

        getDistance(tripList)

    }

    private fun getDistance(list: List<EventsAndAttraction>) {

        var hashMap: HashMap<String, String> = HashMap<String, String>()
        var destination: String = ""
        if (list.isNotEmpty()) {
            hashMap["origins"] =
                currentLocation.latitude.toString() + "," + currentLocation.longitude.toString()

//            hashMap["destination"] =
//                list[0].latitude + "," + list[0].longitude

            list.map {
                destination += it.latitude + "," + it.longitude + "|"
            }

            hashMap["destinations"] = destination
            hashMap["mode"] = travelMode


            hashMap["key"] = getString(R.string.map_key)

            travelModeViewModel.getDistance(
                map = hashMap
            )
        }

    }


}