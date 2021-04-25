package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.sitemap.local.IbeconITemsSiteMap
import com.app.dubaiculture.databinding.FragmentYourJourneyBinding
import com.app.dubaiculture.databinding.ItemsYourJourneyBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter.YourJourneyItems
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.Constants
import com.estimote.coresdk.recognition.packets.Beacon
import com.estimote.coresdk.service.BeaconManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class YourJourneyFragment : BaseBottomSheetFragment<FragmentYourJourneyBinding>() {
    private val siteMapViewModel: SiteMapViewModel by viewModels()
    private var beconList = ArrayList<IbeconITemsSiteMap>()
    private var sortedBeconList = ArrayList<IbeconITemsSiteMap>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        beaconMonitoring()
        subscribeUiEvents(siteMapViewModel)
        arguments?.apply {
            beconList =
                this.getParcelableArrayList(Constants.NavBundles.BECON_LIST)!!
        }
        //for testing purpose
//        rvBecons()

    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentYourJourneyBinding.inflate(inflater, container, false)

    private fun beaconMonitoring() {
        application.beaconManager.apply {
            setRangingListener(BeaconManager.BeaconRangingListener { region, beacons ->

                val nearestBeacon: Beacon = beacons[0]
                siteMapViewModel.showToast("IBecon is Detected...")
                nearestBeacon.uniqueKey
                rvBecons(nearestBeacon.proximityUUID.toString())
            })
        }
    }



    // for testing purpose

    private fun rvBecons() {
        binding.rvIbeacons.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
        beconFilter(beconList,"1234").forEach {
            groupAdapter.add(YourJourneyItems<ItemsYourJourneyBinding>(object : RowClickListener {
                override fun rowClickListener(position: Int) {
                    val beconObj = beconList[position]
                    val bundle = Bundle()
                    bundle.putParcelable(Constants.NavBundles.BECON_OBJECT, beconObj)
                    navigate(R.id.action_yourJourneyFragment_to_ibeconDescFragment, bundle)
                }

            },
                ibeconITemsSiteMap = it,
                resLayout = R.layout.items_your_journey
            ))
        }
    }


    // for live scenario

    private fun rvBecons(uuid : String) {
        binding.rvIbeacons.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
        beconFilter(beconList,uuid).forEach {
            groupAdapter.add(YourJourneyItems<ItemsYourJourneyBinding>(object : RowClickListener {
                override fun rowClickListener(position: Int) {
                    val beconObj = beconList[position]
                    val bundle = Bundle()
                    bundle.putParcelable(Constants.NavBundles.BECON_OBJECT, beconObj)
                    navigate(R.id.action_yourJourneyFragment_to_ibeconDescFragment, bundle)
                }
            },
                ibeconITemsSiteMap = it,
                resLayout = R.layout.items_your_journey
            ))
        }
    }

    private fun beconFilter(beconList : ArrayList<IbeconITemsSiteMap>,uuid : String) : ArrayList<IbeconITemsSiteMap>{
        beconList.forEach {
            if(it.deviceID == uuid ){
                it.isVisited = true
                sortedBeconList.add(it)
            }else{
                it.isVisited = false
                sortedBeconList.add(it)
            }
        }
        sortedBeconList.sortByDescending  {
            it.isVisited
        }
        return sortedBeconList

    }
}


// first beconList loop
// match uuid if uuid match isvisited true too
//sort isVisited true top
// isVisited false alpha disable