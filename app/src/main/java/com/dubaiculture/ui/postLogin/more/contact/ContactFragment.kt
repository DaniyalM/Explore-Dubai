package com.dubaiculture.ui.postLogin.more.contact

import android.Manifest
import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.dubaiculture.R
import com.dubaiculture.data.repository.attraction.local.models.SocialLink
import com.dubaiculture.data.repository.more.local.ContactCenterLocation
import com.dubaiculture.data.repository.more.local.ContactCenterReach
import com.dubaiculture.databinding.FragmentContactBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.attractions.utils.SocialNetworkUtils
import com.dubaiculture.ui.postLogin.more.viewmodel.MoreViewModel
import com.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class ContactFragment : BaseFragment<FragmentContactBinding>(), View.OnClickListener,
    OnMapReadyCallback {
    private val moreViewModel: MoreViewModel by viewModels()
    private lateinit var mapView: MapView
    private lateinit var contactCenterLocation: ContactCenterLocation
    private val socialList = ArrayList<SocialLink>()
    private lateinit var contactCenterReach: ContactCenterReach
    private lateinit var map: GoogleMap

    @Inject
    lateinit var locationManager: LocationManager

    @Inject
    lateinit var locationHelper: LocationHelper
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentContactBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backArrowRTL(binding.imgClose)
        binding.fragment = this
        subscribeUiEvents(moreViewModel)
        mapSetUp(savedInstanceState)
        callingObserver()
        binding.let {
            it.imgClose.setOnClickListener(this)
            it.imgFb.setOnClickListener(this)
            it.imgTwitterAttraction.setOnClickListener(this)
            it.instagram.setOnClickListener(this)
            it.imgYoutube.setOnClickListener(this)
            it.callUs.setOnClickListener(this)
            it.emailLl.setOnClickListener(this)
            it.faxLl.setOnClickListener(this)
            it.websiteLl.setOnClickListener(this)
            it.llShareFeedBack.setOnClickListener(this)
            it.getDirection.setOnClickListener(this)

        }

        binding.imgLinkedinAttraction.setOnClickListener(this)
    }
    private fun mapSetUp(savedInstanceState: Bundle?) {
        if (!this::mapView.isInitialized) {

            mapView = binding.root.findViewById(R.id.map)
            mapView.let {
                it.getMapAsync(this)
                it.onCreate(savedInstanceState)

            }
        }
    }
    private fun callingObserver() {
        moreViewModel.contactUs(getCurrentLanguage().language)
        moreViewModel.contactUs.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                binding.contactCenter = it
                contactCenterLocation = it.contactCenterLocation
                socialList.addAll(it.socialLinks)
                contactCenterReach = it.contactCenterReach
                moreViewModel.setPinOnMap(map, contactCenterLocation)
                binding.tvNumber.setOnLongClickListener {
//                    val clipboard: ClipboardManager? =
//                        activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
//                    val clip = ClipData.newPlainText(label, text)
//                    clipboard?.setPrimaryClip(clip)
                    true
                }

            }
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_share_feed_back -> {
                if(application.auth.isGuest){
                    navigate(R.id.action_contactFragment_to_post_login_bottom_navigation)
                }else{
                    navigate(R.id.action_contactFragment_to_sharedFeeback)
                }
            }
            R.id.img_close -> {
                back()
            }
            R.id.imgFb -> {
                SocialNetworkUtils.getFacebookPage(
                    socialList.get(0).facebookPageLink,activity
                )

            }
            R.id.imgTwitterAttraction -> {
                SocialNetworkUtils.openUrl(
                    socialList.get(0).twitterPageLink,
                    activity,
                    isTwitter = true
                )
            }
            R.id.instagram -> {
                SocialNetworkUtils.openUrl(
                    socialList.get(0).instagramPageLink,
                    activity,
                    isInstagram = true
                )
            }
            R.id.imgYoutube -> {
                SocialNetworkUtils.openUrl(
                    socialList.get(0).youtubePageLink,
                    activity,
                    isYoutube = true
                )
            }
            R.id.imgLinkedinAttraction -> {
                SocialNetworkUtils.openUrl(
                    socialList.get(0)!!.linkedInPageLink,
                    activity,
                    isLinkedIn = true
                )
            }
            R.id.call_us -> {
                openDiallerBox(contactCenterReach.callContent)
            }
            R.id.email_ll -> {
                openEmailbox(contactCenterReach.emailContent)
            }
            R.id.fax_ll -> {

            }
            R.id.website_ll -> {
               openWebURL(contactCenterReach.websiteContent)
            }
            R.id.getDirection -> {
                locationPermission()
            }
        }
    }
    private fun locationPermission() {
        val quickPermissionsOption = QuickPermissionsOptions(
            handleRationale = false
        )
        activity.runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            options = quickPermissionsOption
        ) {
            moreViewModel.showLoader(true)
            if (!locationHelper.isLocationEnabled()) {
                moreViewModel.showLoader(false)
                moreViewModel.showErrorDialog(message = resources.getString(R.string.turn_on))
            }
            locationHelper.locationSetUp(
                object : LocationHelper.LocationLatLng {
                    override fun getCurrentLocation(location: Location) {
                        moreViewModel.showLoader(false)
                        navigateToGoogleMap(
                            location.latitude.toString(),
                            location.longitude.toString(),
                            contactCenterLocation.mapLatitude,
                            contactCenterLocation.mapLongitude
                        )
                        Timber.e("Current Location ${location.latitude}")

                    }
                },
                locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        moreViewModel.showLoader(false)
                        navigateToGoogleMap(
                            locationResult.lastLocation.latitude.toString(),
                            locationResult.lastLocation.longitude.toString(),
                            contactCenterLocation.mapLatitude,
                            contactCenterLocation.mapLongitude
                        )
                        Timber.e("onLocationResult ${locationResult.lastLocation.latitude}")
                    }
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
    }
}