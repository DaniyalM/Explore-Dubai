package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.sitemap.local.IbeconITemsSiteMap
import com.app.dubaiculture.databinding.FragmentIbeconBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.app.dubaiculture.utils.Constants
import com.bumptech.glide.RequestManager
import com.estimote.coresdk.service.BeaconManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IbeaconFragment : BaseDialogFragment<FragmentIbeconBinding>(), View.OnClickListener {
    @Inject
    lateinit var glide: RequestManager
    private val siteMapViewModel: SiteMapViewModel by viewModels()
    private val beconList = ArrayList<IbeconITemsSiteMap>()
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
        arguments.let {
            siteMapViewModel.siteMap(it?.getString(Constants.NavBundles.ATTRACTION_ID).toString(),
                getCurrentLanguage().language)
        }
        binding.imgClose.setOnClickListener(this)
        callingObserver()
        backArrowRTL(binding.imgClose)
        binding.constBottomSheet.setOnClickListener {

            val bundle = Bundle()
            bundle.putParcelableArrayList(Constants.NavBundles.BECON_LIST,
                beconList as ArrayList<out Parcelable>)
            findNavController().navigate(R.id.action_ibeconFragment_to_yourJourneyFragment, bundle)

        }

//        binding.constBottomSheet.setOnTouchListener(object : View.OnTouchListener{
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                findNavController().navigate(R.id.action_ibeconFragment_to_yourJourneyFragment)
//                return true
//            }
//
//        })

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

                glide.load(BuildConfig.BASE_URL + it.ibeconImg)
                    .into(binding.siteMap)
                binding.count.text = it.ibeconItems?.size.toString()
                it.ibeconItems?.forEach {
                    beconList.add(it)
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