package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentIbeconBinding
import com.app.dubaiculture.databinding.FragmentIbeconDescBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.viewmodel.IbeconViewModel
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.SiteMapAdapter
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.recognition.packets.Beacon
import com.estimote.coresdk.service.BeaconManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class IbeconFragment : BaseFragment<FragmentIbeconBinding>(),View.OnClickListener {

    private  val siteMapViewModel : SiteMapViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIbeconBinding.inflate(inflater,container,false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(siteMapViewModel)
        binding.imgClose.setOnClickListener(this)
        beaconMonitoring()



//        callingObserver()
    }


    private fun beaconMonitoring(){
        application.beaconManager.apply {
            setRangingListener(BeaconManager.BeaconRangingListener { _, beacons ->
//
//                val nearestBeacon:Beacon= beacons[0]
//                nearestBeacon.uniqueKey


            })


        }


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close -> {
                back()
            }
        }
    }

    private fun callingObserver(){
        siteMapViewModel.siteMapData.observe(viewLifecycleOwner){
            it.let {
                groupAdapter.add(SiteMapAdapter<SiteViewMapItemsBinding>(
                    siteMapModel = it,
                    resLayout = R.layout.site_view_map_items
                ))
            }
        }
    }
}