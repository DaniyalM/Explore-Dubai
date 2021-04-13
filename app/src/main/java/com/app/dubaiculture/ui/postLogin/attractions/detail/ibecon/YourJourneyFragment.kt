package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.sitemap.local.IbeconITemsSiteMap
import com.app.dubaiculture.databinding.FragmentYourJourneyBinding
import com.app.dubaiculture.databinding.ItemsYourJourneyBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter.YourJourneyItems
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.SiteMapAdapter
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.Constants
import com.estimote.coresdk.service.BeaconManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class YourJourneyFragment : BaseBottomSheetFragment<FragmentYourJourneyBinding>() {
    private val siteMapViewModel: SiteMapViewModel by viewModels()
    private  var beconList = ArrayList<IbeconITemsSiteMap>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        beaconMonitoring()
        subscribeUiEvents(siteMapViewModel)
        arguments?.apply {
            beconList =  this.getParcelableArrayList<IbeconITemsSiteMap>(Constants.NavBundles.BECON_LIST)!!
        }
        rvBecons()
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

    private fun rvBecons(){
        binding.rvIbeacons.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = groupAdapter
        }
        beconList.forEach {
            groupAdapter.add( YourJourneyItems<ItemsYourJourneyBinding>(object : RowClickListener{
                override fun rowClickListener(position: Int) {
                    dismiss()
                    val beconObj = beconList[position]
                    val bundle = Bundle()
                    bundle.putParcelable(Constants.NavBundles.BECON_OBJECT, beconObj)
                    navigate(R.id.action_yourJourneyFragment_to_ibeconDescFragment,bundle)
                }

            },
                ibeconITemsSiteMap = it,
                resLayout = R.layout.items_your_journey
            ))
        }
    }
}