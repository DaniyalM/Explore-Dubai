package com.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
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
import javax.inject.Inject

@AndroidEntryPoint
class IbeaconFragment : BaseFragment<FragmentIbeconBinding>(), View.OnClickListener {
    @Inject
    lateinit var glide: RequestManager
    private val siteMapViewModel: SiteMapViewModel by viewModels()
    private val beaconSharedViewModel: BeaconSharedViewModel by activityViewModels()
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

//    override fun onDestroyView() {
//        super.onDestroyView()
//        beaconUtils.beaconDisconnect()
//    }

    private fun beaconMonitoring() {
        beaconUtils.beaconConnect()
        beaconUtils.beaconManager.apply {

            setMonitoringListener(object : BeaconManager.BeaconMonitoringListener {
                override fun onEnteredRegion(
                    beaconRegion: BeaconRegion?,
                    beacons: MutableList<Beacon>?
                ) {

                    beacons?.let {
                        val nearestBeacon: Beacon = it[0]
                        PushNotificationManager.showNotification(
                            context,
                            "Beacon Scanning",
                            "Beacon Detected : ${it[0].proximityUUID}", null
                        )
                        lifecycleScope.launch {
                            attractionId?.let {
                                beaconSharedViewModel.markAsVisited(
                                    it,
                                    nearestBeacon.uniqueKey
                                )
                            }

                        }
                    }

//                    beconFilterForNotification(beconList, Constants.IBecons.UUID_BECON)
                }

                override fun onExitedRegion(beaconRegion: BeaconRegion?) {
                    siteMapViewModel.showToast(resources.getString(R.string.you_are_leaving))
                }

            })
        }
    }

    private fun callingObserver() {
        siteMapViewModel.siteMapData.observe(viewLifecycleOwner) {
            it.let {
                glide.load(BuildConfig.BASE_URL + it.ibeconImg)
                    .into(binding.siteMap)
                binding.count.text = it.ibeconItems?.size.toString()

            }
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