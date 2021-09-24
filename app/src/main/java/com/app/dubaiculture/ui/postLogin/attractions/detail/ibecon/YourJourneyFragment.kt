package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.app.dubaiculture.databinding.FragmentYourJourneyBinding
import com.app.dubaiculture.databinding.ItemsYourJourneyBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter.YourJourneyItems
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.preLogin.PreLoginActivity
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.IBecons.UUID_BECON
import com.app.dubaiculture.utils.PushNotificationManager.showNotification
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.recognition.packets.Beacon
import com.estimote.coresdk.service.BeaconManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YourJourneyFragment : BaseBottomSheetFragment<FragmentYourJourneyBinding>() {
    private val siteMapViewModel: SiteMapViewModel by viewModels()
    private var beconList = ArrayList<BeaconItems>()
    private var sortedBeconList = ArrayList<BeaconItems>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        beaconMonitoring()

        subscribeUiEvents(siteMapViewModel)
        arguments?.apply {
            beconList =
                    this.getParcelableArrayList(Constants.NavBundles.BECON_LIST)!!
        }
        //for testing purpose
//        rvBecons()
        if(!beconList.isNullOrEmpty()){
            binding.count.text = beconList.size.toString()
            rvBecons()
        }

    }


    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?,
    ) = FragmentYourJourneyBinding.inflate(inflater, container, false)

    private fun beaconMonitoring() {
        application.beaconManager.apply {
            this.startRanging(application.region)
            this.startMonitoring(application.region)
            setRangingListener(BeaconManager.BeaconRangingListener { region, beacons ->

                val nearestBeacon: Beacon = beacons[0]
                siteMapViewModel.showToast("IBeacon is Detected...")
//                nearestBeacon.uniqueKey
                rvBecons(nearestBeacon.proximityUUID.toString())
                beconFilterForNotification(beconList,UUID_BECON)

            })
           setMonitoringListener(object : BeaconManager.BeaconMonitoringListener {
                override fun onEnteredRegion(beaconRegion: BeaconRegion?, beacons: MutableList<Beacon>?) {
                    Toast.makeText(activity,"Monitoring has been started", Toast.LENGTH_SHORT).show()
                    beconFilterForNotification(beconList,UUID_BECON)
                }

                override fun onExitedRegion(beaconRegion: BeaconRegion?) {
                    siteMapViewModel.showToast("You are leaving the Region")
                }

            })



        }
    }

    override fun onPause() {
        application.beaconManager.stopRanging(application.region)
        super.onPause()
    }


    // for testing purpose

    private fun rvBecons() {
        binding.rvIbeacons.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
        beconFilter(beconList, "12345").forEach {
            groupAdapter.add(YourJourneyItems<ItemsYourJourneyBinding>(object : RowClickListener {
                override fun rowClickListener(position: Int) {
                    val beconObj = beconList[position]
                    val bundle = Bundle()
                    bundle.putParcelable(Constants.NavBundles.BECON_OBJECT, beconObj)
//                    navigate(R.id.action_yourJourneyFragment_to_ibeconDescFragment, bundle)
                }

                override fun rowClickListener(position: Int, imageView: ImageView) {

                }

            },
                    beaconItems = it,
                    resLayout = R.layout.items_your_journey
            ))
        }
    }


    // for live scenario

    private fun rvBecons(uuid: String) {
        binding.rvIbeacons.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
        beconFilter(beconList, uuid).forEach {
            groupAdapter.add(YourJourneyItems<ItemsYourJourneyBinding>(object : RowClickListener {
                override fun rowClickListener(position: Int) {
                    val beconObj = beconList[position]
                    val bundle = Bundle()
                    bundle.putParcelable(Constants.NavBundles.BECON_OBJECT, beconObj)
//                    navigate(R.id.action_yourJourneyFragment_to_ibeconDescFragment, bundle)
                }

                override fun rowClickListener(position: Int, imageView: ImageView) {

                }
            },
                    beaconItems = it,
                    resLayout = R.layout.items_your_journey
            ))
        }
    }

    private fun beconFilter(beconList: ArrayList<BeaconItems>, uuid: String): ArrayList<BeaconItems> {

        beconList.forEach {
            if (it.deviceID == uuid) {
                it.isVisited = true
                sortedBeconList.add(it)
            } else {
                it.isVisited = false
                sortedBeconList.add(it)
            }
        }
        sortedBeconList.sortByDescending {
            it.isVisited
        }
        return sortedBeconList

    }

    private fun beconFilterForNotification(beconList: ArrayList<BeaconItems>, uuid: String){

        beconList.forEach {
            val intent  = Intent(requireContext(), PreLoginActivity::class.java)
            val resultPendingIntent =
                PendingIntent.getActivity(requireContext(),1,intent, PendingIntent.FLAG_UPDATE_CURRENT)
            if (it.deviceID == uuid) {
                showNotification(activity,
                    it.title,
                    it.summary,resultPendingIntent)
            }
        }
    }
}


// first beconList loop
// match uuid if uuid match isvisited true too
//sort isVisited true top
// isVisited false alpha disable