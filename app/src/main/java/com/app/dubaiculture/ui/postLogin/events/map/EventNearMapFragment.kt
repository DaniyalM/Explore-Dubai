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


class EventNearMapFragment : BaseFragment<FragmentEventNearMapBinding>() {
    lateinit var eventNearAdapter : EventNearMapAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentEventNearMapBinding.inflate(inflater,container,false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            // get near event model
        }
        rvSetUp()

    }
    private fun rvSetUp(){
        eventNearAdapter = EventNearMapAdapter()
        binding.rvNearEvent.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = eventNearAdapter
        }
        eventNearAdapter.events = createTestItems()
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
}