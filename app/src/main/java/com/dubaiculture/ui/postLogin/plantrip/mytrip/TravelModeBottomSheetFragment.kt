package com.dubaiculture.ui.postLogin.plantrip.mytrip

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.dubaiculture.databinding.FragmentTravelModeBottomSheetBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TravelModeViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.location.LocationHelper
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

    val args: TravelModeBottomSheetFragmentArgs by navArgs()

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
//        locationPermission()
        subscribeUiEvents(travelModeViewModel)
        subscribeToObservables()
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



                    }
                },
                locationCallback
            )
        }
    }

    private fun subscribeToObservables() {

//        tripSharedViewModel.travelMode.observe(viewLifecycleOwner) {
//            when (it) {
//                Constants.TRAVEL_MODE.DRIVING -> {
//                    onDrivingClicked()
//                }
//                Constants.TRAVEL_MODE.WALKING -> {
//                    onWalkingClicked()
//                }
//                Constants.TRAVEL_MODE.TRANSIT -> {
//                    onTrainClicked()
//                }
//                Constants.TRAVEL_MODE.BICYCLING -> {
//                    onBusClicked()
//                }
//
//            }
//        }

        travelModeViewModel.distanceResponse.observe(viewLifecycleOwner) {
            it.rows[0].elements.map {
                if (it.status == "ZERO_RESULTS") {
                    showAlert(Constants.TRAVEL_MODE.ERROR)
                    return@observe
                }
            }
            tripSharedViewModel._travelMode.value = travelMode


        }
        travelModeViewModel.directionDistanceResponse.observe(viewLifecycleOwner){
            tripSharedViewModel.mapSingleDistanceInList(it,travelMode,args.eventId)
            dismiss()
        }
        tripSharedViewModel.tripList.observe(viewLifecycleOwner) {
            if (it != null) {
                tripList = it
                getTravelMode()

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
        val indices =
            list!!.mapIndexedNotNull { index, event -> if (event.id == args.eventId) index else null }

        var hashMap: HashMap<String, String> = HashMap<String, String>()
        var destination: String = ""
        if (list.isNotEmpty()) {

            repeat(indices.size) {
                hashMap["origins"] =
                    list[it].latitude.toString() + "," + list[it].longitude.toString()

                hashMap["destinations"] =
                    list[it + 1].latitude.toString() + "," + list[it + 1].longitude.toString()
            }

            hashMap["mode"] = travelMode

            hashMap["language"] = getCurrentLanguage().language

            hashMap["key"] = getString(R.string.map_key)

            travelModeViewModel.getDistance(
                map = hashMap
            )
        }

    }

    private fun getTravelMode() {
        var travelMode: String = tripList.single { s -> s.id == args.eventId }.travelMode

        when (travelMode) {
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

}