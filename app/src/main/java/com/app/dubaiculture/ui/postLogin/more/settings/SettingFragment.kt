package com.app.dubaiculture.ui.postLogin.more.settings

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.databinding.FragmentSettingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.profile.viewmodels.ProfileViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.enableLocationFromSettings
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(), View.OnClickListener {
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var userSettings: UserSettings

    @Inject
    lateinit var locationHelper: LocationHelper


    var isTouched = false


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchLoc.isChecked=locationHelper.isLocationEnabled()

        subscribeUiEvents(profileViewModel)
        binding.apply {
            backArrowRTL(imgClose)
            switchLoc.isChecked = locationHelper.isLocationEnabled()
            imgClose.setOnClickListener {
                back()
            }
        }
        initiateRequest()
        profileViewModel.getSettings()
        subscribeToObservable()


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

            when (it) {
                is Result.Success -> {
                    it.value.getContentIfNotHandled()?.let {
                        userSettings = it
                        binding.noti.setOnClickListener(this)
                        binding.reset.setOnClickListener(this)
                        markPushNotificationSwitch()

                    }
                }
                is Result.Failure -> handleApiError(it, profileViewModel)
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

                false
            }
            setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (isTouched) {
                        isTouched = false
                        userSettings.turnOnLocation = isChecked
                        locationPermission()
                        binding.switchLoc.isChecked = userSettings.turnOnLocation

//                        if (!isChecked&&locationHelper.isLocationEnabled()){
//
//                        }else
//                        binding.switchLoc.isChecked = userSettings.turnOnLocation
                        profileViewModel.updateSettings(userSettings)
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.switchLoc.isChecked=locationHelper.isLocationEnabled()
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
            activity.enableLocationFromSettings()

        }
    }
}