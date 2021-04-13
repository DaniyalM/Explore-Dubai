package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentIbeconBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.SiteMapAdapter
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.estimote.coresdk.service.BeaconManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_your_journey.*

@AndroidEntryPoint
class IbeaconFragment : BaseDialogFragment<FragmentIbeconBinding>(), View.OnClickListener {

    private val siteMapViewModel: SiteMapViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentIbeconBinding.inflate(inflater, container, false)

    override fun getTheme() = R.style.FullScreenDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)

    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.window!!.apply {
                setLayout(width, height)
                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insetsController?.hide(WindowInsets.Type.statusBars())
                } else {
                    setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }

            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(siteMapViewModel)

        binding.imgClose.setOnClickListener(this)
        beaconMonitoring()
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        siteMapViewModel.showToast("Open")
//                        val fragManager: FragmentManager = myContext.getFragmentManager()
//                        YourJourneyFragment().show(getSupportFragmentManager(),
//                            "Dialog")
//                        YourJourneyFragment().test()
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        siteMapViewModel.showToast("Closed")
                    }
                    else -> siteMapViewModel.showToast("Persistent Bottom Sheet")
                }
            }
        })

    }


    private fun beaconMonitoring() {
        application.beaconManager.apply {
            setRangingListener(BeaconManager.BeaconRangingListener { _, beacons ->
//
//                val nearestBeacon:Beacon= beacons[0]
//                nearestBeacon.uniqueKey
            })
        }
    }

    private fun callingObserver() {
        siteMapViewModel.siteMapData.observe(viewLifecycleOwner) {
            it.let {
                it.ibeconItems.forEach {
                    groupAdapter.add(SiteMapAdapter<SiteViewMapItemsBinding>(
                        ibeconITemsSiteMap = it,
                        resLayout = R.layout.site_view_map_items
                    ))
                }

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