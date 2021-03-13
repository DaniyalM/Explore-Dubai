package com.app.dubaiculture.ui.postLogin.events.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.FragmentEventNearMapBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.adapters.EventNearMapAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventNearMapFragment : BaseFragment<FragmentEventNearMapBinding>(),View.OnClickListener,
    OnMapReadyCallback {
    lateinit var eventNearAdapter : EventNearMapAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentEventNearMapBinding.inflate(inflater,container,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.header.back.setOnClickListener(this)
        arguments?.let {
            // get near event model
        }
        rvSetUp()
        mapSetUp()
    }
    private fun rvSetUp(){
        eventNearAdapter = EventNearMapAdapter()
        binding.rvNearEvent.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = eventNearAdapter
        }
        eventNearAdapter.events = createTestItems()
    }
    private fun mapSetUp() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment!!.getMapAsync(this)
    }
    private fun createTestItems(): List<Events> =
        mutableListOf<Events>().apply {
            repeat((1..4).count()) {
                add(Events(
                    id = it.toString(),
                    title = "The Definitive Guide to an Uncertain World",
                    category = "WORKSHOP",
                    image = "",
                    toDate = "12 - 15 Nov, 20",
                ))
            }
        }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.back->{
                back()
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        val trafficDigitalLatLng = LatLng(24.8623, 67.0627)
        map!!.addMarker(MarkerOptions()
            .position(trafficDigitalLatLng)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.radius_pin_map)))
            .title = "Traffic Digital"
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                trafficDigitalLatLng, 12.0f
            )
        )
        map.cameraPosition.target


    }
}