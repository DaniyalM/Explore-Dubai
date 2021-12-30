package com.dubaiculture.ui.postLogin.plantrip.mytrip

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.Duration
import com.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.dubaiculture.databinding.FragmentMyTripListingBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.navGraphActivity.NavGraphActivity
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.DatesAdapter
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.MyTripAdapter
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.DateClickListener
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.MyTripClickListener
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.MyTripListingViewModel
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
class MyTripListingFragment : BaseBottomSheetFragment<FragmentMyTripListingBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMyTripListingBinding.inflate(inflater, container, false)

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private val myTripListingViewModel: MyTripListingViewModel by viewModels()
    private lateinit var currentLocation: Location
    private lateinit var travelMode: String


    @Inject
    lateinit var locationHelper: LocationHelper

    private lateinit var datesAdapter: DatesAdapter
    private lateinit var myTripAdapter: MyTripAdapter

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            //  locationIsEmpty(locationResult.lastLocation)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.view = this
        binding.viewModel = myTripListingViewModel
        subscribeUiEvents(myTripListingViewModel)
        setupRV()

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
//        locationPermission()
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

                        navigateByDirections(MyTripListingFragmentDirections.actionMyTripListingToTravelModeDialog())
//                        navigate(R.id.action_my_trip_listing_to_travel_mode_dialog)

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

        tripSharedViewModel.eventAttractionResponse.observe(viewLifecycleOwner) {
            binding.tripId = it.tripId
            Location(LocationManager.GPS_PROVIDER).apply {
                latitude = it.location.latitude.toDouble()
                longitude = it.location.longitude.toDouble()
                currentLocation = this
            }
        }
        tripSharedViewModel.travelMode.observe(viewLifecycleOwner) {
            travelMode = it
        }

        tripSharedViewModel.tripList.observe(viewLifecycleOwner) {
            if (it != null) {
                myTripAdapter.submitList(it)
            }
        }
        tripSharedViewModel.eventAttractionList.observe(viewLifecycleOwner) {
            if (it != null)
                getDistance(it)
        }

        myTripListingViewModel.distanceResponse.observe(viewLifecycleOwner) {
            it.rows[0].elements.map {
                if (it.status == "ZERO_RESULTS") {
                    showAlert(Constants.TRAVEL_MODE.ERROR)
                    return@observe
                }
            }
            tripSharedViewModel.mapDistanceInList(it, travelMode)
        }

        tripSharedViewModel.dates.observe(viewLifecycleOwner) {
            binding.tvDate.text = it.single { it.isSelected }.dayDate.substring(3)
            tripSharedViewModel.updateLocalDistance(currentLocation, it.single { it.isSelected })
           // tripSharedViewModel.filterEvents(it.single { it.isSelected })



            datesAdapter.submitList(it)

        }

        tripSharedViewModel.showSave.observe(viewLifecycleOwner) {
            if (it) {
                binding.btnNext.visibility = View.VISIBLE
                binding.btnEditDur.visibility = View.VISIBLE
                binding.btnDeleteDur.visibility = View.GONE
            } else {
                binding.btnNext.visibility = View.GONE
                binding.btnEditDur.visibility = View.GONE
                binding.btnDeleteDur.visibility = View.VISIBLE
            }

        }

        myTripListingViewModel.deleteTripStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                if (it) {
                    tripSharedViewModel.updateTripItem(binding.tripId!!)
                    navigate(R.id.action_tripFragmentListing_to_delete)
                }
            }
        }


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

            myTripListingViewModel.getDistance(
                map = hashMap
            )
        }

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

    fun onBackPressed() {
        back()
//    navigateByDirections(MyTripFragmentDirections.actionTripFragmentToTripDetailParentFragment())
    }

    fun onSaveTripClicked() {
        navigate(R.id.action_myTrip_listing_to_myTripNameDialog)
    }

    fun onEditTripClicked() {
//        navigate(R.id.action_my_trip_listing_to_tripFragment)


        showAlertDialog(
            message = getString(R.string.EditTripAlert),
            textPositive = getString(R.string.yes),
            textNegative = getString(R.string.no),
            actionNegative = {

            },
            actionPositive = {
                val intent = Intent(
                    requireActivity(),
                    NavGraphActivity::class.java
                )
                intent.putExtra(
                    Constants.NavBundles.GRAPH_ID,
                    R.navigation.plan_trip_parent_navigation
                )
                startActivity(intent)
                Handler().postDelayed({
                    back()
                    back()
                }, 1000)
            }
        )
    }

    fun onDeleteClicked(tripId: String) {

        navigateByDirections(MyTripListingFragmentDirections.actionMyTripToDeleteDialog(tripId))

    }

}