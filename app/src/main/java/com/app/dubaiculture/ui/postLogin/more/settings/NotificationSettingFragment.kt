package com.app.dubaiculture.ui.postLogin.more.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.data.repository.settings.local.UserSettings
import com.app.dubaiculture.databinding.FragmentNotificationSettingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.profile.viewmodels.ProfileViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.SETTINGS_BUNDLE
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotificationSettingFragment : BaseFragment<FragmentNotificationSettingBinding>() {
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var userSettings: UserSettings
    var isTouched = false


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNotificationSettingBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            userSettings = it.getParcelable(SETTINGS_BUNDLE)!!
            userSettings = userSettings.copy(culture = getCurrentLanguage().language)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(profileViewModel)
        backArrowRTL(binding.imgClose)
        markPushNotificationSwitch()
        markLocationBaseSwitch()
        markEmailSwitch()
        markSmsSwitch()
        binding.imgClose.setOnClickListener {
            back()
        }
    }


    fun markPushNotificationSwitch() {
        binding.pushNotSwitch.apply {
            setOnCheckedChangeListener(null)
            isChecked = userSettings.pushNotification
            setOnTouchListener { view, motionEvent ->
                isTouched = true
                false
            }
            setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (isTouched) {
                        isTouched = false
                        userSettings.pushNotification = isChecked
                        profileViewModel.updateSettings(userSettings)
                    }
                }
            }
        }

    }

    fun markLocationBaseSwitch() {
        binding.locationBasedSwitch.apply {
            setOnCheckedChangeListener(null)
            isChecked = userSettings.locationBasedNotifications
            setOnTouchListener { view, motionEvent ->
                isTouched = true
                false
            }
            setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (isTouched) {
                        isTouched = false
                        userSettings.locationBasedNotifications = isChecked
                        profileViewModel.updateSettings(userSettings)
                    }
                }
            }
        }
    }

    fun markEmailSwitch() {
        binding.emailSwitch.apply {
            setOnCheckedChangeListener(null)
            isChecked = userSettings.email
            setOnTouchListener { view, motionEvent ->
                isTouched = true
                false
            }
            setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (isTouched) {
                        isTouched = false
                        userSettings.email = isChecked
                        profileViewModel.updateSettings(userSettings)
                    }
                }
            }
        }
    }

    fun markSmsSwitch() {
        binding.smsSwitch.apply {
            setOnCheckedChangeListener(null)
            isChecked = userSettings.sms
            setOnTouchListener { view, motionEvent ->
                isTouched = true
                false
            }
            setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (isTouched) {
                        isTouched = false
                        userSettings.sms = isChecked
                        profileViewModel.updateSettings(userSettings)
                    }
                }
            }
        }
    }
}