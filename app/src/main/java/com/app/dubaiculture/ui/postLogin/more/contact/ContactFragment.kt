package com.app.dubaiculture.ui.postLogin.more.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.SocialLink
import com.app.dubaiculture.data.repository.more.local.ContactCenterLocation
import com.app.dubaiculture.data.repository.more.local.ContactCenterReach
import com.app.dubaiculture.databinding.FragmentContactBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.utils.SocialNetworkUtils
import com.app.dubaiculture.ui.postLogin.more.viewmodel.MoreViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactFragment : BaseFragment<FragmentContactBinding>(), View.OnClickListener,
    OnMapReadyCallback {
    private val moreViewModel: MoreViewModel by viewModels()
    private lateinit var mapView: MapView
    private lateinit var contactCenterLocation: ContactCenterLocation
    private val socialList = ArrayList<SocialLink>()
    private lateinit var contactCenterReach: ContactCenterReach
    private lateinit var map: GoogleMap

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentContactBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        }

        binding.imgLinkedinAttraction.setOnClickListener(this)
    }
    private fun mapSetUp(savedInstanceState: Bundle?) {
        if (!this::mapView.isInitialized) {

            mapView = binding.root.findViewById(R.id.map)
            mapView?.let {
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

            }
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_share_feed_back->{
                navigate(R.id.action_contactFragment_to_sharedFeeback)
            }
            R.id.img_close -> {
                back()
            }
            R.id.imgFb -> {
                SocialNetworkUtils.openUrl(
                    socialList.get(0)!!.facebookPageLink,
                    activity,
                    isFacebook = true
                )
            }
            R.id.imgTwitterAttraction -> {
                SocialNetworkUtils.openUrl(
                    socialList.get(0)!!.twitterPageLink,
                    activity,
                    isTwitter = true
                )
            }
            R.id.instagram -> {
                SocialNetworkUtils.openUrl(
                    socialList.get(0)!!.instagramPageLink,
                    activity,
                    isInstagram = true
                )
            }
            R.id.imgYoutube -> {
                SocialNetworkUtils.openUrl(
                    socialList.get(0)!!.youtubePageLink,
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
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.BASE_URL+contactCenterReach.websiteContent))
                startActivity(browserIntent)
            }
            R.id.getDirection -> {

            }

        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
    }

}