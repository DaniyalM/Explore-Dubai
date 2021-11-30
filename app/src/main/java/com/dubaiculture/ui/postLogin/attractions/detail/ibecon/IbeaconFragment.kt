package com.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.dubaiculture.databinding.FragmentIbeconBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.attractions.detail.ibecon.viewmodel.BeaconSharedViewModel
import com.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.dubaiculture.utils.BeaconUtils
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.PushNotificationManager
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.recognition.packets.Beacon
import com.estimote.coresdk.service.BeaconManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class IbeaconFragment : BaseFragment<FragmentIbeconBinding>(), View.OnClickListener {
    @Inject
    lateinit var glide: RequestManager
    private val siteMapViewModel: SiteMapViewModel by viewModels()
    private val beaconSharedViewModel: BeaconSharedViewModel by activityViewModels()
    private lateinit var beaconItems: List<BeaconItems>
    //    private val siteMapViewModel: SiteMapViewModel by viewModels()

    private var attractionId: String? = null

    //    lateinit var region: BeaconRegion
    lateinit var beaconManager: BeaconManager

    @Inject
    lateinit var beaconUtils: BeaconUtils
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIbeconBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(siteMapViewModel)
        beaconMonitoring()
        arguments?.let {
            attractionId = it.getString(Constants.NavBundles.ATTRACTION_ID)
        }
        attractionId?.let {
            siteMapViewModel.siteMap(it, getCurrentLanguage().language)
        }

        binding.imgClose.setOnClickListener(this)
        callingObserver()
        backArrowRTL(binding.imgClose)


        binding.constBottomSheet.setOnClickListener {


            navigateByDirections(
                IbeaconFragmentDirections.actionIbeconFragmentToYourJourneyBeaconFragment(
                    attractionId!!
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        beaconUtils.beaconManager.disconnect()
    }

    private fun beaconMonitoring() {
        beaconUtils.beaconManager.apply {
            setMonitoringListener(object : BeaconManager.BeaconMonitoringListener {
                override fun onEnteredRegion(
                    beaconRegion: BeaconRegion?,
                    beacons: MutableList<Beacon>?
                ) {
                    beaconUtils.beaconManager.startRanging(beaconRegion)
                    PushNotificationManager.showNotification(
                        context,
                        "Beacon Scanning",
                        "Beacon Detected", null
                    )
                    navigateByDirections(
                        IbeaconFragmentDirections.actionIbeconFragmentToYourJourneyBeaconFragment(
                            attractionId!!
                        )
                    )


                }

                override fun onExitedRegion(beaconRegion: BeaconRegion?) {
                    beaconManager.stopRanging(beaconRegion)
                    PushNotificationManager.showNotification(
                        context,
                        "Beacon Scanning",
                        resources.getString(R.string.you_are_leaving), null
                    )

                }

            })
            setRangingListener(BeaconManager.BeaconRangingListener { beaconRegion, beacons ->

                beacons?.let {
                    if (it.isNotEmpty()) {
                        val nearestBeacon: Beacon = it[0]
                        lifecycleScope.launch {
                            beaconItems.map { beacons ->
                                 if ((beacons.proximityID.equals(nearestBeacon.proximityUUID) || beacons.proximityID.toUpperCase().equals(nearestBeacon.proximityUUID)) && !beacons.visited) {
                                    attractionId?.let {
                                        beaconSharedViewModel.markAsVisited(
                                            it,
                                            nearestBeacon.uniqueKey
                                        )

                                    }
                                    return@map beacons.copy(visited = true)
                                } else
                                    return@map beacons
                            }.let {
                                beaconItems = it
                            }


                        }
                    }


                }
            })
        }

    }

    private fun callingObserver() {
        beaconSharedViewModel.beaconVisted.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                lifecycleScope.launch {
                    beaconSharedViewModel.markAsVisited(attractionId!!, it.uniqueKey)

                }
            }
        }

        siteMapViewModel.beacons.observe(viewLifecycleOwner) {
            beaconItems = it
            beaconUtils.beaconManager.connect(object : BeaconManager.ServiceReadyCallback {
                override fun onServiceReady() {
                    it.forEach {
                        if (it.proximityID.isNotEmpty()) {
                            beaconUtils.beaconManager
                            beaconUtils.beaconManager.startRanging(
                                BeaconRegion(
                                    it.deviceID,
                                    UUID.fromString(it.proximityID), 0, 0
                                )
                            )
                        }

                    }

                }
            })
        }
        siteMapViewModel.siteMapData.observe(viewLifecycleOwner) {
            glide.load(BuildConfig.BASE_URL + it.ibeconImg)
                .into(binding.siteMap)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_close -> {
                back()
            }
        }
    }

}