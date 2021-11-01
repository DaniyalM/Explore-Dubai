package com.app.dubaiculture.ui.postLogin.plantrip.mytrip

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.Duration
import com.app.dubaiculture.data.repository.trip.local.EventsAndAttraction
import com.app.dubaiculture.data.repository.trip.remote.response.Route
import com.app.dubaiculture.databinding.FragmentMyTripBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.DatesAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.MyTripAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.DateClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.mytrip.adapter.clicklisteners.MyTripClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.MyTripViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.app.dubaiculture.utils.event.Event
import com.app.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
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

    //    private var mapView: MapView? = null
    private lateinit var datesAdapter: DatesAdapter
    private lateinit var myTripAdapter: MyTripAdapter

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
    ) = FragmentMyTripBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        subscribeUiEvents(myTripViewModel)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapFrag) as SupportMapFragment
        mapFragment.getMapAsync(this)



        setupRV()

    }

    private fun getDirections(list: List<EventsAndAttraction>) {

        var hashMap: HashMap<String, String> = HashMap<String, String>()

        if(list.isNotEmpty()) {
            hashMap["origin"] =
                currentLocation.latitude.toString() + "," + currentLocation.longitude.toString()

            hashMap["destination"] =
                list[0].latitude + "," + list[0].longitude

            list.subList(1, list.size).map {
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
                    }

                    override fun rowClickListener(
                        eventAttraction: EventsAndAttraction,
                        position: Int
                    ) {
                    }

                }
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

                        currentLocation = location
                        subscribeToObservables()


                    }
                },
                locationCallback
            )
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
//        mapSetUp(savedInstanceState)
//        locationPermission()
    }

    private fun subscribeToObservables() {


        tripSharedViewModel.eventAttractionList.observe(viewLifecycleOwner) {
            getDirections(it)
            addMarkers(it)
            myTripAdapter.submitList(it)

        }

        tripSharedViewModel.dates.observe(viewLifecycleOwner) {
            binding.tvDate.text = it.single { it.isSelected }.dayDate.substring(3)
            tripSharedViewModel.filterEvents(it.single { it.isSelected })
            datesAdapter.submitList(it)

        }
        myTripViewModel.directionResponse.observe(viewLifecycleOwner) {
            drawPolyline(it.routes)
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
        locationPermission()


    }



    fun onBackPressed() {

        tripSharedViewModel._showPlan.value = Event(false)
        navigateBack()
    }


    fun onBottomSheetClicked() {

        navigate(R.id.action_my_trip_to_my_trip_listing)

    }

    fun onSaveTripClicked() {

        navigate(R.id.action_myTrip_bottom_sheet_to_myTripNameDialog)

    }




}