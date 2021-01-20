package com.app.dubaiculture.ui.postLogin.events

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentEventsBinding
import com.app.dubaiculture.ui.base.BaseFragment

class EventsFragment:BaseFragment<FragmentEventsBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentEventsBinding.inflate(inflater,container,false)
}