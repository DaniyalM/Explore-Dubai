package com.app.dubaiculture.ui.postLogin.events.map

import android.R.attr
import android.R.attr.bitmap
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventNearMapBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.adapters.EventNearMapAdapter
import com.app.dubaiculture.utils.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.event_search_toolbar.view.*
import kotlinx.android.synthetic.main.toolbar_layout_event_detail.*


@AndroidEntryPoint
class EventNearMapFragment : BaseFragment<FragmentEventNearMapBinding>(), View.OnClickListener,
    OnMapReadyCallback {
    lateinit var eventNearAdapter: EventNearMapAdapter
    private  var mapList = ArrayList<Events>()
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.header.back.setOnClickListener(this)
        backArrowRTL(binding.header.back)

        rvSetUp()
        mapSetUp()
    }

    private fun rvSetUp() {
        eventNearAdapter = EventNearMapAdapter(isArabic())
        binding.rvNearEvent.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = eventNearAdapter
        }
        eventNearAdapter.events = mapList
    }

    private fun mapSetUp() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment!!.getMapAsync(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                back()
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        currentLocation(map)
        setupMap(map!!)
        pinsOnMap(mapList, map)
    }

    private fun pinsOnMap(list: List<Events>, map: GoogleMap) {
        list.forEach {
            val locationObj =
                LatLng(it.latitude.toString().ifEmpty { "24.83250180519734" }.toDouble(),
                        it.longitude.toString().ifEmpty { "67.08119661055807" }.toDouble())
            if (it.distance!! <= 6.0)
                map.addMarker(MarkerOptions().position(locationObj)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_within_distance_calendar))
                        .title(it.title))
            else{
           val marker =     map.addMarker(MarkerOptions().position(locationObj)
                   .title(it.title))
            addMarker(marker)}

        }
    }

    private fun setupMap(googleMap: GoogleMap?) {
        googleMap?.addCircle(
                CircleOptions()
                        .center(LatLng(mapList[0].currentLat, mapList[0].currentLng))
                        .radius(5000.0)
                        .strokeWidth(1f)
                        .strokeColor(ContextCompat.getColor(requireContext(), R.color.map_radius_color))
                        .fillColor(0x220000FF)
//                .fillColor(ContextCompat.getColor(requireContext(), R.color.map_radius_color))
        )
    }

    private fun currentLocation(googleMap: GoogleMap?) {
        val trafficDigitalLatLng = LatLng(mapList[0].currentLat, mapList[0].currentLng)

     googleMap!!.addMarker(MarkerOptions()
             .position(trafficDigitalLatLng)
             .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_current)))

        googleMap!!.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        trafficDigitalLatLng, 12.0f
                )
        )
        googleMap.cameraPosition.target
    }

 fun addMarker(marker: Marker){

     val uri: Uri = Uri.parse("https://dc.qa.greenlightlabs.tech/api/-/media/DC/Home-Modules/museums-icon.svg")

     Glide.with(requireContext())
             .asBitmap()
             .load(uri)
             .into(object : SimpleTarget<Bitmap>() {
                 override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                     if (bitmap != null) {
                         val icon = BitmapDescriptorFactory.fromBitmap(resource)
                         marker.setIcon(icon)
                     }
                 }

             })


 }
 }
