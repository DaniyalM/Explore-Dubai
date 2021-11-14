package com.dubaiculture.ui.postLogin.more.settings

import android.Manifest
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.settings.local.UserSettings
import com.dubaiculture.databinding.FragmentSettingBinding
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.profile.viewmodels.ProfileViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.enableLocationFromSettings
import com.dubaiculture.utils.handleApiError
import com.dubaiculture.utils.location.LocationHelper
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

        subscribeUiEvents(profileViewModel)
        binding.apply {
            backArrowRTL(imgClose)
            switchLoc.isChecked = locationHelper.isLocationEnabled()
            imgClose.setOnClickListener {
                back()
            }
        }
        initiateRequest()
        subscribeToObservable()
        markPushNotificationSwitch()
        darkModeEnable()

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
                        userSettings = it.copy(culture = getCurrentLanguage().language)
                        binding.noti.setOnClickListener(this)
                        binding.reset.setOnClickListener(this)


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
                    culture=getCurrentLanguage().language
                    profileViewModel.updateSettings(this, true)
                }
            }
        }

    }

private  fun darkModeEnable() {
        val preferenceRepository = (requireActivity().application as ApplicationEntry).preferenceRepository
        preferenceRepository.isDarkThemeLive.observe(viewLifecycleOwner) { isDarkTheme ->
            isDarkTheme?.let { binding.switchDarkMode.isChecked = it }
        }
        binding.switchDarkMode.setOnCheckedChangeListener { _, checked ->

            preferenceRepository.isDarkTheme = checked
        }
    }

  private  fun markPushNotificationSwitch() {
        binding.switchLoc.apply {
            setOnCheckedChangeListener(null)
            isChecked = locationHelper.isLocationEnabled()
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
                        profileViewModel.updateSettings(userSettings)
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.switchLoc.isChecked = locationHelper.isLocationEnabled()
        if (this::userSettings.isInitialized) {
            userSettings.turnOnLocation = binding.switchLoc.isChecked
            profileViewModel.updateSettings(userSettings)
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
            activity.enableLocationFromSettings()

        }
    }
}