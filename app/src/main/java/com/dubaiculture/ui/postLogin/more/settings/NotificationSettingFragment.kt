package com.dubaiculture.ui.postLogin.more.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dubaiculture.data.repository.settings.local.UserSettings
import com.dubaiculture.databinding.FragmentNotificationSettingBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.profile.viewmodels.ProfileViewModel
import com.dubaiculture.utils.Constants.NavBundles.SETTINGS_BUNDLE
import com.dubaiculture.utils.firebase.subscribeToTopic
import com.dubaiculture.utils.firebase.unSubscribeFromTopic
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
            getCurrentLanguage().language.let {
                if (isChecked) {
                    val id = it + "_" + "${application.auth.user?.userId}"
                    subscribeToTopic(topic = "AndroidBroadcast_$it")
                    subscribeToTopic(topic = "AndroidBroadcast_$id")
                } else {

                    val id = it + "_" + "${application.auth.user?.userId}"
                    unSubscribeFromTopic(topic="AndroidBroadcast_ar")
                    unSubscribeFromTopic(topic="AndroidBroadcast_en")
                    unSubscribeFromTopic(topic = "AndroidBroadcast_$id")
                }
            }
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
                        getCurrentLanguage().language.let {
                            if (isChecked) {
                                val id = it + "_" + "${application.auth.user?.userId}"
                                subscribeToTopic(topic = "AndroidBroadcast_$it")
                                subscribeToTopic(topic = "AndroidBroadcast_$id")
                            } else {

                                    val id = it + "_" + "${application.auth.user?.userId}"
                                    unSubscribeFromTopic(topic="AndroidBroadcast_ar")
                                    unSubscribeFromTopic(topic="AndroidBroadcast_en")
                                    unSubscribeFromTopic(topic = "AndroidBroadcast_$id")
                            }
                        }

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