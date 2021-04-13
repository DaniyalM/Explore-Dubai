package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentNearYouBinding
import com.app.dubaiculture.databinding.FragmentYourJourneyBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.SiteMapAdapter
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.estimote.coresdk.service.BeaconManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YourJourneyFragment : BaseBottomSheetFragment<FragmentYourJourneyBinding>() {
    private val siteMapViewModel: SiteMapViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(siteMapViewModel)
        siteMapViewModel.showToast("OnActivityCreated.")

    }



    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentYourJourneyBinding.inflate(inflater,container,false)
    private fun beaconMonitoring() {
        application.beaconManager.apply {
            setRangingListener(BeaconManager.BeaconRangingListener { _, beacons ->
//
//                val nearestBeacon:Beacon= beacons[0]
//                nearestBeacon.uniqueKey
            })
        }
    }


}