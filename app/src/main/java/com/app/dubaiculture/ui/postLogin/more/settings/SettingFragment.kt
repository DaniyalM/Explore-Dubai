package com.app.dubaiculture.ui.postLogin.more.settings

import android.Manifest
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.databinding.FragmentSettingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.profile.viewmodels.ProfileViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.GpsStatus
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(), View.OnClickListener {
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var userSettings: UserSettings

    @Inject
    lateinit var locationHelper: LocationHelper

    @Inject
    lateinit var locationManager: LocationManager
    var isTouched = false

    private val gpsObserver = Observer<GpsStatus> { status ->
        status?.let {
            updateGpsCheckUI(status)
        }
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Timber.e("onLocationResult ${locationResult.lastLocation.latitude}")
        }
    }

    private fun updateGpsCheckUI(status: GpsStatus) {
        when (status) {
            is GpsStatus.Enabled -> {
                binding.apply {
                    switchLoc.isChecked = true
                }

            }
            is GpsStatus.Disabled -> {
                binding.apply {
                    switchLoc.isChecked = false
                }
            }
        }
    }

    private fun subscribeToGpsListener() = profileViewModel.gpsStatusLiveData
        .observe(viewLifecycleOwner, gpsObserver)


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUiEvents(profileViewModel)
        subscribeToGpsListener()
        initiateRequest()
        profileViewModel.getSettings()
        subscribeToObservable()

        binding.imgClose.setOnClickListener {
            back()
        }

    }

    private fun initiateRequest() {
        binding.swipeRefresh.apply {
            setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
            )
            setOnRefreshListener {
                binding.swipeRefresh.isRefreshing = false
                profileViewModel.getSettings()

            }
        }
    }

    private fun subscribeToObservable() {
        profileViewModel.userSettings.observe(viewLifecycleOwner) {

            when(it){
                is Result.Success ->{
                    it.value.getContentIfNotHandled()?.let {
                        userSettings = it
                        userSettings.let {
                            binding.noti.setOnClickListener(this)
                            binding.reset.setOnClickListener(this)
                            markPushNotificationSwitch()
                        }
                    }
                }
                is Result.Failure ->handleApiError(it,profileViewModel)
            }




        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.noti -> {
                navigate(
                    R.id.action_settingFragment_to_notificationSettingFragment,
                    Bundle().apply {
                        this.putParcelable(
                            Constants.NavBundles.SETTINGS_BUNDLE,
                            userSettings as Parcelable
                        )
                    })
            }
            R.id.reset -> {
                userSettings.apply {
                    turnOnLocation = false
                    pushNotification = false
                    sms = false
                    email = false
                    locationBasedNotifications = false
                    profileViewModel.updateSettings(this, true)
                }
            }
        }

    }


    fun markPushNotificationSwitch() {
        binding.switchLoc.apply {
            setOnCheckedChangeListener(null)
            isChecked = userSettings.turnOnLocation
            setOnTouchListener { _, _ ->
                isTouched = true
//                if (locationHelper.isLocationEnabled(locationManager)){
//
//                }else {
//                    locationPermission()
//                }
                false
            }
            setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (isTouched) {
                        isTouched = false
                        userSettings.turnOnLocation = isChecked
                        profileViewModel.updateSettings(userSettings)
                    }
                }
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
            locationHelper.locationSetUp(
                object : LocationHelper.LocationLatLng {
                    override fun getCurrentLocation(location: Location) {
                        Timber.e("Current Location ${location.latitude}")
                    }
                },
                activity, locationCallback
            )
        }
    }
}