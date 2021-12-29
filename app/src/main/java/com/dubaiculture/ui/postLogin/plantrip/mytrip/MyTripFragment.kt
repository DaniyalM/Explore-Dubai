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
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.Duration
import com.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.dubaiculture.data.repository.trip.remote.response.Route
import com.dubaiculture.databinding.FragmentMyTripBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.navGraphActivity.NavGraphActivity
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.DatesAdapter
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.MyTripAdapter
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.DateClickListener
import com.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.MyTripClickListener
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.MyTripViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.event.Event
import com.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.Behavior.DragCallback
import com.google.maps.android.PolyUtil
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MyTripFragment : BaseFragment<FragmentMyTripBinding>(), OnMapReadyCallback {

    private lateinit var currentLocation: Location
    private lateinit var mMap: GoogleMap
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private val myTripViewModel: MyTripViewModel by viewModels()
    private lateinit var travelMode: String

    //    private var mapView: MapView? = null
    private lateinit var datesAdapter: DatesAdapter
    private lateinit var myTripAdapter: MyTripAdapter
    private var mapView: MapView? = null

    //    private lateinit var binding: FragmentMyTripListingBinding
    @Inject
    lateinit var locationHelper: LocationHelper

    lateinit var tripId: String

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            //  locationIsEmpty(locationResult.lastLocation)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMyTripBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        binding.viewModel = myTripViewModel
        subscribeUiEvents(myTripViewModel)
        backArrowRTL(binding.ivClose)

        val params: CoordinatorLayout.LayoutParams =
            binding.appbarLayout.layoutParams as CoordinatorLayout.LayoutParams

        val behavior = AppBarLayout.Behavior()
        behavior.setDragCallback(object : DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })
        params.behavior = behavior

//        backArrowRTL(binding.ivClose)
//        navigateByDirections(MyTripFragmentDirections.actionMyTripFragmentToTripListFragment())

//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true /* enabled by default */) {
//                override fun handleOnBackPressed() {
//
//                }
//            }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        setupRV()
    }


    private fun getDirections(list: List<EventsAndAttraction>) {

        var hashMap: HashMap<String, String> = HashMap<String, String>()

        if (list.isNotEmpty()) {
            hashMap["origin"] =
                currentLocation.latitude.toString() + "," + currentLocation.longitude.toString()

            hashMap["destination"] =
                list.last().latitude + "," + list.last().longitude

            list.subList(0, list.size - 1).map {
                hashMap["waypoints"] = it.latitude + "," + it.longitude
            }

            hashMap["key"] = getString(R.string.map_key)

            myTripViewModel.getDirections(
                map = hashMap
            )
        }

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

//                        navigate(R.id.action_my_trip_to_travel_mode_dialog)
                        navigateByDirections(MyTripFragmentDirections.actionMyTripToTravelModeDialog())

                    }

                    override fun rowClickListener(
                        eventAttraction: EventsAndAttraction,
                        position: Int
                    ) {
                    }

                    override fun rowClickListenerDirections(latitude: String, longitude: String) {
                        if (!latitude.isNullOrEmpty() && !longitude.isNullOrEmpty()) {
                            // open google map application
                            navigateToGoogleMap(
                                currentLocation.latitude.toString(),
                                currentLocation.longitude.toString(),
                                latitude,
                                longitude
                            )
                        }                    }

                },
                getCurrentLanguage()
            )
            adapter = myTripAdapter


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

//                        currentLocation = location
                        mMap.clear()


                    }
                },
                locationCallback
            )
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (!this::mMap.isInitialized) {
            mapSetUp(savedInstanceState)
        } else {
            subscribeToObservables()
        }


//
//        locationPermission()

//        locationPermission()
    }

    private fun mapSetUp(savedInstanceState: Bundle?) {

        mapView = binding.root.findViewById(com.dubaiculture.R.id.map)
        mapView?.let {
            it.getMapAsync(this)
            it.onCreate(savedInstanceState)

        }
    }

    private fun subscribeToObservables() {


        tripSharedViewModel.eventAttractionResponse.observe(viewLifecycleOwner) {
            binding.tripId = it.tripId
            tripId = it.tripId
            mMap.clear()
            Location(LocationManager.GPS_PROVIDER).apply {
                latitude = it.location.latitude.toDouble()
                longitude = it.location.longitude.toDouble()
                currentLocation = this
            }

            //   addBottomSheet()


        }
        tripSharedViewModel.travelMode.observe(viewLifecycleOwner) {
            travelMode = it
        }

        tripSharedViewModel.tripList.observe(viewLifecycleOwner) {
            if (it != null) {
                myTripAdapter.submitList(it)
            }
        }



        tripSharedViewModel.dates.observe(viewLifecycleOwner) {
            binding.tvDate.text = it.single { it.isSelected }.dayDate.substring(3)

            //
            tripSharedViewModel.updateLocalDistance(currentLocation, it.single { it.isSelected })
            //
//            tripSharedViewModel.filterEvents(it.single { it.isSelected })


            datesAdapter.submitList(it)

        }

        myTripViewModel.distanceResponse.observe(viewLifecycleOwner) {
            it.rows[0].elements.map {
                if (it.status == "ZERO_RESULTS") {
                    showAlert(Constants.TRAVEL_MODE.ERROR)
                    return@observe
                }
            }
            tripSharedViewModel.mapDistanceInList(it, travelMode)
        }

        myTripViewModel.directionResponse.observe(viewLifecycleOwner) {
            drawPolyline(it.routes)
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

        myTripViewModel.deleteTripStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                if (it) {
                    tripSharedViewModel.updateTripItem(binding.tripId!!)
                    back()
                }
            }
        }
        tripSharedViewModel.eventAttractionList.observe(viewLifecycleOwner) {
            if (it != null) {
                //    tripSharedViewModel.filterList()
                getDirections(it)
                getDistance(it)
                addMarkers(it)

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

            myTripViewModel.getDistance(
                map = hashMap
            )
        }

    }

    private fun drawPolyline(routes: List<Route>) {

        val polyoptions = PolylineOptions()
        polyoptions.color(ContextCompat.getColor(context!!, R.color.polyline_color))
        polyoptions.width(5f)
        polyoptions.addAll(PolyUtil.decode(routes[0].overviewPolyline.points))
        mMap.addPolyline(polyoptions)


    }

    private fun addMarkers(markers: List<EventsAndAttraction>) {
        val builder = LatLngBounds.Builder()

        mMap.addMarker(
            MarkerOptions()
                .position(
                    LatLng(
                        currentLocation.latitude.toDouble(),
                        currentLocation.longitude.toDouble()
                    )
                )
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location))
                .title("Current Location")
        )

        builder.include(LatLng(currentLocation.latitude, currentLocation.longitude))

        markers.map {
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.trip_location_circle))
                    .title(it.title)
            )
            builder.include(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))

        }
        val bounds = builder.build()


        val cu = CameraUpdateFactory.newLatLngBounds(bounds, 50)

        mMap.setOnMapLoadedCallback(
            OnMapLoadedCallback {

                mMap.animateCamera(cu)
            })

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        subscribeToObservables()
    }

    fun onBackPressed() {
        showAlert(
            message = getString(R.string.tripCloseAlert),
            textPositive = getString(R.string.yes),
            textNegative = getString(R.string.no),
            actionNegative = {

            },
            actionPositive = {
                back()
            }
        )
    }


    fun onBottomSheetClicked() {
//        navigate(R.id.action_my_trip_to_my_trip_listing)
    }

    fun onSaveTripClicked() {
        navigate(R.id.action_myTrip_bottom_sheet_to_myTripNameDialog)
    }

    fun onEditTripClicked() {
//        navigate(R.id.action_myTrip_to_tripFragment)
        showAlert(
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
                }, 1000)
            }
        )


    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
        tripSharedViewModel._showPlan.value = Event(false)
        tripSharedViewModel._eventAttractionResponse.value = null
        tripSharedViewModel._eventAttractionList.value = null


    }

    fun onDeleteClicked(tripId: String) {

        navigateByDirections(MyTripFragmentDirections.actionMyTripToDeleteDialog(tripId))

    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()

    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()

    }


}