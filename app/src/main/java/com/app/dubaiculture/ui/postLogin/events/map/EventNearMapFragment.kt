package com.app.dubaiculture.ui.postLogin.events.map

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventNearMapBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.DirectionClickListener
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventNearMapAdapter
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.EventUtilFunctions.showToast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EventNearMapFragment : BaseFragment<FragmentEventNearMapBinding>(), View.OnClickListener,
    OnMapReadyCallback {
    lateinit var eventNearAdapter: EventNearMapAdapter
    private  var mapList = ArrayList<Events>()
    private lateinit var mapView: MapView
    lateinit var googleMap: GoogleMap
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentEventNearMapBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            mapList =  this.getParcelableArrayList<Events>(Constants.NavBundles.EVENT_MAP_LIST)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.back.setOnClickListener(this)
        backArrowRTL(binding.header.back)
        mapSetUp(savedInstanceState)
        rvSetUp()

    }

    private fun rvSetUp() {
        eventNearAdapter = EventNearMapAdapter(isArabic(), object : RowClickListener{
            override fun rowClickListener(position: Int) {

                val eventObj = mapList[position]
                val bundle = Bundle()
                bundle.putParcelable(Constants.NavBundles.EVENT_OBJECT,
                        eventObj)
                navigate(R.id.action_eventNearMapFragment2_to_eventDetailFragment2,bundle)

            }

            override fun rowClickListener(position: Int, imageView: ImageView) {

            }
        },object : DirectionClickListener{
            override fun directionClickListener(position: Int) {
                if (!mapList[position].latitude.isNullOrEmpty()) {
                    val uri = Constants.GoogleMap.LINK_URI + mapList[position].currentLat.toString() + "," + mapList[position].currentLng + Constants.GoogleMap.DESTINATION + mapList[position].latitude.toString() + "," + mapList[position].longitude
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    intent.setPackage(Constants.GoogleMap.PACKAGE_NAME_GOOGLE_MAP)
                    try {
                        startActivity(intent)
                    } catch (ex: ActivityNotFoundException) {
                      showToast("Please install a Google map application",requireContext())
                    }
                }
            }

        })
        binding.rvNearEvent.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = eventNearAdapter
        }
        eventNearAdapter.events = mapList
    }

    private fun mapSetUp(savedInstanceState: Bundle?) {
        if(!this::mapView.isInitialized){
            mapView = binding.root.findViewById(R.id.google_map)
            mapView.let {
                it.getMapAsync(this)
                it.onCreate(savedInstanceState)
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                back()
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
        googleMap.clear()
        setupMap(googleMap)
        pinsOnMap(mapList, googleMap)
        currentLocation(googleMap)

    }

    private fun pinsOnMap(list: List<Events>, map: GoogleMap?) {
        list.forEach {
            val locationObj =
                LatLng(
                    it.latitude.ifEmpty { "24.83250180519734" }.toDouble(),
                    it.longitude.ifEmpty { "67.08119661055807" }.toDouble()
                )
            if (it.distance <= 6.0)
                map?.addMarker(
                    MarkerOptions().position(locationObj)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.events_map))
                        .title(it.title)
                )
            else
                map?.addMarker(
                    MarkerOptions().position(locationObj)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.events_away))
                        .title(it.title)
                )
        }
    }

    private fun setupMap(googleMap: GoogleMap?) {
        googleMap?.addCircle(
            CircleOptions()
                .center(LatLng(mapList[0].currentLat, mapList[0].currentLng))
                .fillColor(getColorWithAlpha(Color.CYAN, 0.15f))
                .strokeColor(getColorWithAlpha(Color.CYAN, 0.15f))
                .radius(5000.0)
                .strokeWidth(1f)
//                .fillColor(ContextCompat.getColor(requireContext(), R.color.map_radius_color))
        )
    }

    private fun currentLocation(googleMap: GoogleMap?) {
        val trafficDigitalLatLng = LatLng(mapList[0].currentLat, mapList[0].currentLng)
        googleMap?.addMarker(MarkerOptions()
            .position(trafficDigitalLatLng)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_current)))
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                trafficDigitalLatLng, 12.0f
            )
        )
        googleMap?.cameraPosition?.target
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()

    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()

    }
    override fun onDestroy() {
        super.onDestroy()
        mapView.onPause()

    }
}