package com.app.dubaiculture.ui.postLogin.streetview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.dubaiculture.databinding.FragmentStreetViewBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.base.BaseStreetViewPanaromaFragment
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.StreetViewPanoramaOptions
import com.google.android.gms.maps.StreetViewPanoramaView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.StreetViewPanoramaCamera
import com.google.android.gms.maps.model.StreetViewPanoramaLink
import com.google.android.gms.maps.model.StreetViewSource

class StreetViewFragment : BaseStreetViewPanaromaFragment<FragmentStreetViewBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentStreetViewBinding.inflate(inflater, container, false)



}